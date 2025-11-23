package com.parkys.musicplayer

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import com.parkys.musicplayer.block.common.log.LogTracer
import com.parkys.musicplayer.block.core.media.service.MusicMediaService

class App : Application() {

    companion object {
        val NAME: String = App::class.java.simpleName
        private lateinit var instance: App
        fun get(): App = instance
    }

    override fun onCreate() {
        super.onCreate()
        LogTracer.i { "$NAME -> onCreate" }
        instance = this


        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        MusicMediaService.launcherPendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


    }
}