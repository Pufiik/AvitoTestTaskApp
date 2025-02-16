package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackService

import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.*
import org.koin.android.ext.android.inject

@OptIn(UnstableApi::class)
class TrackService : MediaSessionService() {

    private val notificationManager: TrackNotificationManager by inject()
    private val mediaSession: MediaSession by inject()



    @UnstableApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.startNotificationService(
                mediaSessionService = this,
                mediaSession = mediaSession
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }



    override fun onDestroy() {
        super.onDestroy()
        mediaSession.apply {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(0)
                player.playWhenReady = false
                player.release()
            }
        }
    }
}
