package com.parkys.musicplayer.block.core.media.provider

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer

object ExoPlayerProvider {

    private var exoPlayer: ExoPlayer? = null

    fun getPlayer(context: Context): ExoPlayer {
        val appContext = context.applicationContext
        return exoPlayer ?: buildPlayer(appContext).also {
            exoPlayer = it
        }
    }

    private fun buildPlayer(context: Context): ExoPlayer {
        val audioAttrs = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()

        return ExoPlayer.Builder(context).build().apply {
            setAudioAttributes(audioAttrs, true)
            setHandleAudioBecomingNoisy(true)
            playWhenReady = false
        }
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }
}
