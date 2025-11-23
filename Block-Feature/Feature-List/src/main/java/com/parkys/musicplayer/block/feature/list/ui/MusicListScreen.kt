package com.parkys.musicplayer.block.feature.list.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.parkys.musicplayer.block.core.media.model.Music
import com.parkys.musicplayer.block.feature.list.presentation.MusicListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicListScreen(
    viewModel: MusicListViewModel,
    onMusicClick: (Music) -> Unit,
) {
    val musicList by viewModel.musicList.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val currentPlayingId by viewModel.currentMusicId.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Music Player") }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            when {
                loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                musicList.isEmpty() -> {
                    Text(
                        text = "No music found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(musicList) { music ->
                            MusicListItem(
                                music = music,
                                isPlaying = (music.id == currentPlayingId),
                                onClick = {
                                    viewModel.selectMusic(music)
                                    onMusicClick(music)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
