package com.parkys.musicplayer.block.core.media.controller

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.parkys.musicplayer.block.common.log.LogTracer
import com.parkys.musicplayer.block.core.media.model.Music
import com.parkys.musicplayer.block.core.media.model.PlayerProgress
import com.parkys.musicplayer.block.core.media.model.PlayerState
import com.parkys.musicplayer.block.core.media.provider.ExoPlayerProvider
import com.parkys.musicplayer.block.core.media.service.MusicMediaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlayerControllerImpl(
    private val context: Context,
    private val scope: CoroutineScope
) : IPlayerController {

    private val player = ExoPlayerProvider.getPlayer(context)

    /** 전체 음악 리스트 */
    private var playlist: List<Music> = emptyList()

    /** 현재 인덱스 */
    private var currentIndex: Int = -1

    /** 현재 재생 중 음악 */
    private val _currentMusic = MutableStateFlow<Music?>(null)
    override val currentMusic: StateFlow<Music?> = _currentMusic.asStateFlow()

    /** 재생 상태 */
    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Idle)
    override val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    /** 진행도 */
    private val _playerProgress = MutableStateFlow(PlayerProgress(0L, 0L, 0L))
    override val playerProgress: StateFlow<PlayerProgress> = _playerProgress.asStateFlow()

    private var progressJob: Job? = null

    init {
        setupPlayerListener()
        startProgressUpdater()
    }


    private fun setupPlayerListener() {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playerState.value = if (isPlaying) PlayerState.Playing else PlayerState.Paused
            }

            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_IDLE -> _playerState.value = PlayerState.Idle
                    Player.STATE_BUFFERING -> _playerState.value = PlayerState.Buffering
                    Player.STATE_READY ->
                        _playerState.value =
                            if (player.isPlaying) PlayerState.Playing else PlayerState.Paused

                    Player.STATE_ENDED -> {
                        _playerState.value = PlayerState.Completed
                    }
                }
            }
        })
    }


    private fun startProgressUpdater() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive) {
                _playerProgress.value = PlayerProgress(
                    duration = player.duration.takeIf { it > 0 } ?: 0L,
                    currentPosition = player.currentPosition,
                    bufferedPosition = player.bufferedPosition
                )
                delay(500)
            }
        }
    }


    override fun setPlaylist(list: List<Music>) {
        playlist = list
    }

    private fun startMediaService(music: Music) {
        val intent = Intent(context, MusicMediaService::class.java).apply {
            putExtra("music", music)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }


    override fun play(music: Music) {
        _currentMusic.value = music

        currentIndex = playlist.indexOfFirst { it.id == music.id }
        startMediaService(music = music)

        val item = MediaItem.fromUri(music.contentUri)
        player.setMediaItem(item)
        player.prepare()
        player.play()

        _playerState.value = PlayerState.Playing
    }

    override fun pause() {
        player.pause()
        _playerState.value = PlayerState.Paused
    }

    override fun resume() {
        player.play()
        _playerState.value = PlayerState.Playing
    }

    override fun next() {
        LogTracer.i { "PlayerControllerImpl -> next [ size: ${playlist.size}, currentIndex: ${currentIndex} ]" }
        if (playlist.isEmpty()) return
        if (currentIndex == -1) return

        val nextIndex = (currentIndex + 1).coerceAtMost(playlist.size - 1)
        currentIndex = nextIndex

        play(playlist[currentIndex])
    }

    override fun previous() {
        LogTracer.i { "PlayerControllerImpl -> previous [ size: ${playlist.size}, currentIndex: ${currentIndex} ]" }
        if (playlist.isEmpty()) return
        if (currentIndex == -1) return

        val prevIndex = (currentIndex - 1).coerceAtLeast(0)
        currentIndex = prevIndex

        play(playlist[currentIndex])
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }
}
