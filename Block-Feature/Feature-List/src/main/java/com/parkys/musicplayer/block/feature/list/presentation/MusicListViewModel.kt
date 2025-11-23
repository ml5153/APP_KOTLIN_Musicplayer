package com.parkys.musicplayer.block.feature.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkys.musicplayer.block.core.media.model.Music
import com.parkys.musicplayer.block.core.media.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicListViewModel(
    private val repository: MusicRepository
) : ViewModel() {

    private val _musicList = MutableStateFlow<List<Music>>(emptyList())
    val musicList: StateFlow<List<Music>> = _musicList

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _currentMusicId = MutableStateFlow<Long?>(null)
    val currentMusicId: StateFlow<Long?> = _currentMusicId

    init {
        loadMusic()
    }

    fun loadMusic() {
        viewModelScope.launch {
            _loading.value = true
            _musicList.value = repository.getAllMusic()
            _loading.value = false
        }
    }

    fun selectMusic(music: Music) {
        _currentMusicId.value = music.id
    }
}
