package com.parkys.musicplayer.block.feature.player.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.parkys.musicplayer.block.core.media.model.PlayerState
import com.parkys.musicplayer.block.feature.player.presentation.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    musicId: Long,
    viewModel: PlayerViewModel,
    onBack: () -> Unit
) {
    val loadedMusic by viewModel.music.collectAsState()

    val currentMusic by viewModel.currentMusic.collectAsState()
    val progress by viewModel.playerProgress.collectAsState()
    val state by viewModel.playerState.collectAsState()

    LaunchedEffect(musicId) {
        viewModel.loadMusic(musicId)
    }

    if (loadedMusic == null) {
        Text("Loading...", modifier = Modifier.padding(20.dp))
        return
    }

    LaunchedEffect(loadedMusic) {
        viewModel.play(loadedMusic!!)
    }

    val displayMusic = currentMusic ?: loadedMusic!!

    Scaffold(
        topBar = {
            TopAppBar(
                title = { "" },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = displayMusic.albumArtUri,
                contentDescription = null,
                modifier = Modifier
                    .size(260.dp)
                    .padding(16.dp)
            )

            Text(text = displayMusic.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = displayMusic.artist, style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(32.dp))

            // SeekBar
            Slider(
                value = progress.currentPosition.toFloat(),
                onValueChange = { newPos ->
                    viewModel.seekTo(newPos.toLong())
                },
                valueRange = 0f..(progress.duration.toFloat().coerceAtLeast(1f)),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "${progress.currentPosition / 1000}초 / ${progress.duration / 1000}초"
            )

            Spacer(Modifier.height(20.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { viewModel.previous() }) {
                    Icon(Icons.Default.SkipPrevious, contentDescription = null)
                }

                if (state == PlayerState.Playing) {
                    IconButton(onClick = { viewModel.pause() }) {
                        Icon(Icons.Default.Pause, contentDescription = null)
                    }
                } else {
                    IconButton(onClick = { viewModel.resume() }) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                    }
                }

                IconButton(onClick = { viewModel.next() }) {
                    Icon(Icons.Default.SkipNext, contentDescription = null)
                }
            }
        }
    }
}
