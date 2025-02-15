package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackService

import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.*
import org.koin.android.ext.android.inject
import org.koin.compose.koinInject

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
//    override fun onCreate() {
//        super.onCreate()
//
//        // Создание MediaSession
//        mediaSession = MediaSession.Builder(this, player)
//            .setSessionActivity(createSessionIntent())
//            .setCallback(object : MediaSession.Callback {
//                override fun onCustomCommand(
//                    session: MediaSession,
//                    controller: MediaSession.ControllerInfo,
//                    customCommand: SessionCommand,
//                    args: Bundle
//                ): ListenableFuture<SessionResult> {
//                    when (customCommand.customAction) {
//                        ACTION_PLAY.customAction -> player.play()
//                        ACTION_PAUSE.customAction -> player.pause()
//                        ACTION_NEXT.customAction -> player.seekToNext()
//                        ACTION_PREV.customAction -> player.seekToPrevious()
//                    }
//                    return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
//                }
//            }).build()
//
//        startForeground(NOTIFICATION_ID, createMediaNotification())
//    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

//    private fun createSessionIntent(): PendingIntent {
//        val intent = Intent(this, MainActivity::class.java)
//        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//    }
//
//    private fun createMediaNotification(): Notification {
//        val playIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_PLAY.customAction), PendingIntent.FLAG_IMMUTABLE)
//        val pauseIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_PAUSE.customAction), PendingIntent.FLAG_IMMUTABLE)
//        val nextIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_NEXT.customAction), PendingIntent.FLAG_IMMUTABLE)
//        val prevIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_PREV.customAction), PendingIntent.FLAG_IMMUTABLE)
//
//        return NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Музыкальный плеер")
//            .setContentText("Воспроизводится трек")
//            .setSmallIcon(R.drawable.stub)
//            .setContentIntent(createSessionIntent())
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setOngoing(true)
//            .addAction(Action(R.drawable.play_white, "Назад", prevIntent))
//            .addAction(Action(R.drawable.play_white, "Пауза", pauseIntent))
//            .addAction(Action(R.drawable.play_white, "Далее", nextIntent))
//            .addAction(Action(R.drawable.play_white,"mm", playIntent))
//            .build()
//    }

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
