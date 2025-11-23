package com.parkys.musicplayer.block.feature.list.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.parkys.musicplayer.block.core.media.model.Music

@Composable
fun MusicListItem(
    music: Music,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    val highlightColor = if (isPlaying)
        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    else
        Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = highlightColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 앨범아트
            AsyncImage(
                model = music.albumArtUri,
                contentDescription = music.title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = music.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
                Text(
                    text = music.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }

            // 재생 중이면 EQ 애니메이션 보여주기
            if (isPlaying) {
                EqualizerAnimation()
            }
        }
    }
}

@Composable
private fun EqualizerAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val barHeights = List(3) { index ->
        infiniteTransition.animateFloat(
            initialValue = 6f,
            targetValue = (16 + index * 5).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(350 + index * 100, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row(
        modifier = Modifier
            .padding(start = 12.dp)
            .height(20.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        barHeights.forEachIndexed { idx, height ->
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(height.value.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(2.dp)
                    )
            )
            if (idx < barHeights.lastIndex) {
                Spacer(Modifier.width(3.dp))
            }
        }
    }
}
