package ru.pugovishnikova.example.avitotesttaskapp.presentation.trackService


import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import ru.pugovishnikova.example.avitotesttaskapp.R

class RunningService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "running-chanel")
            .setSmallIcon(R.drawable.stub)
            .setContentTitle("Action")
            .setContentText("Music")
            .build()
        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }
}