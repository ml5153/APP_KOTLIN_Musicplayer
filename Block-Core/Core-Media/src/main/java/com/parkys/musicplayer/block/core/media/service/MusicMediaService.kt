package com.parkys.musicplayer.block.core.media.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.MediaStyleNotificationHelper
import com.parkys.musicplayer.block.core.media.model.Music
import com.parkys.musicplayer.block.core.media.provider.ExoPlayerProvider

class MusicMediaService : MediaSessionService() {

    private lateinit var mediaSession: MediaSession

    private var currentMusic: Music? = null

    companion object {
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "player_channel"
        const val CHANNEL_NAME = "Music Playback"

        var launcherPendingIntent: PendingIntent? = null

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.let {
            currentMusic = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableExtra("music", Music::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelableExtra("music")
            }
        }

        currentMusic?.let {
            val updateNoti = buildInitialNotification()
            startForeground(NOTIFICATION_ID, updateNoti)
        }
        return START_STICKY
    }


    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        val player = ExoPlayerProvider.getPlayer(applicationContext)

        mediaSession = MediaSession.Builder(this, player).build()

        val initialNotification = buildInitialNotification()
        startForeground(NOTIFICATION_ID, initialNotification)
    }

    override fun onDestroy() {
        mediaSession.release()
        ExoPlayerProvider.release()
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession =
        mediaSession

    @androidx.annotation.OptIn(UnstableApi::class)
    override fun onUpdateNotification(
        session: MediaSession,
        startInForegroundRequired: Boolean
    ) {
        val notification = createPlayerNotification(this, session)

        if (startInForegroundRequired) {
            startForeground(NOTIFICATION_ID, notification)
        } else {
            startForeground(NOTIFICATION_ID, notification)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                stopForeground(STOP_FOREGROUND_DETACH)
            } else {
                stopForeground(false)
            }
        }
    }

    private fun buildInitialNotification(): Notification {
        val title = currentMusic?.title ?: "Music Player"
        val artist = currentMusic?.artist ?: "Initializing..."
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle(title)
            .setContentText(artist)
            .setContentIntent(launcherPendingIntent)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    private fun createPlayerNotification(
        context: Context,
        session: MediaSession
    ): Notification {

        val player = session.player

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle(player.mediaMetadata.title ?: "Music Player")
            .setContentText(player.mediaMetadata.artist ?: "")
            .setOnlyAlertOnce(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(player.isPlaying)
            .setStyle(
                MediaStyleNotificationHelper.MediaStyle(session)
                    .setShowActionsInCompactView(1)
            )
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_previous,
                    "Prev",
                    session.sessionActivity
                )
            )
            .addAction(
                if (player.isPlaying)
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_pause,
                        "Pause",
                        session.sessionActivity
                    )
                else
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_play,
                        "Play",
                        session.sessionActivity
                    )
            )
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_next,
                    "Next",
                    session.sessionActivity
                )
            )
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )

            channel.setSound(null, null)
            channel.enableVibration(false)

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
