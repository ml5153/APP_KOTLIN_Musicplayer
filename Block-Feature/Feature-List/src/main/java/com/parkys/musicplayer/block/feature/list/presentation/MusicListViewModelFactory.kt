package com.parkys.musicplayer.block.feature.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parkys.musicplayer.block.core.media.repository.MusicRepository

class MusicListViewModelFactory(
    private val repository: MusicRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicListViewModel(repository) as T
    }
}