package com.parkys.musicplayer.block.core.media.controller

import com.parkys.musicplayer.block.core.media.model.Music
import com.parkys.musicplayer.block.core.media.model.PlayerProgress
import com.parkys.musicplayer.block.core.media.model.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface IPlayerController {
    val currentMusic: StateFlow<Music?>
    val playerState: StateFlow<PlayerState>
    val playerProgress: StateFlow<PlayerProgress>

    fun setPlaylist(list: List<Music>)

    fun play(music: Music)
    fun pause()
    fun resume()
    fun next()
    fun previous()
    fun seekTo(position: Long)
}