package com.parkys.musicplayer.block.feature.player.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parkys.musicplayer.block.core.media.controller.PlayerControllerImpl
import com.parkys.musicplayer.block.core.media.repository.MusicRepositoryImpl
import kotlinx.coroutines.MainScope


class PlayerViewModelFactory(
    private val appContext: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val repository = MusicRepositoryImpl(appContext)
        val controller = PlayerControllerImpl(appContext, MainScope())

        return PlayerViewModel(repository, controller) as T
    }
}
