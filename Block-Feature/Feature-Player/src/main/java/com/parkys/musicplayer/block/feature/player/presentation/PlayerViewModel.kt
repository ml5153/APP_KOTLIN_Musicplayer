package com.parkys.musicplayer.block.feature.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkys.musicplayer.block.core.media.controller.IPlayerController
import com.parkys.musicplayer.block.core.media.model.Music
import com.parkys.musicplayer.block.core.media.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val musicRepository: MusicRepository,
    private val controller: IPlayerController
) : ViewModel() {

    private val _music = MutableStateFlow<Music?>(null)
    val music: StateFlow<Music?> = _music

    val currentMusic = controller.currentMusic
    val playerState = controller.playerState
    val playerProgress = controller.playerProgress


    fun setPlaylist(list: List<Music>) {
        controller.setPlaylist(list)
    }

    fun loadMusic(id: Long) {
        viewModelScope.launch {
            _music.value = musicRepository.getMusicById(id)
        }
    }

    fun play(music: Music) = controller.play(music)
    fun pause() = controller.pause()
    fun resume() = controller.resume()
    fun next() = controller.next()
    fun previous() = controller.previous()
    fun seekTo(pos: Long) = controller.seekTo(pos)
}
