package com.mytodo.todo.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.mytodo.todo.R

class NotificationService : Service() {

//    override fun onBind(p0: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//
//        when (intent?.action) {
//            Actions.START.toString() -> start()
////            Actions.STOP.toString() -> stopSelf()
//            "STOP" -> stopSelf()
//        }
//        return super.onStartCommand(intent, flags, startId)
//    }
//
//    private fun start() {
//        val stopIntent = Intent("STOP")
//        val stopPendingIntent = PendingIntent.getBroadcast(
//            this,
//            0,
//            stopIntent,
////            PendingIntent.FLAG_UPDATE_CURRENT,
//            PendingIntent.FLAG_IMMUTABLE
//        )
//
//
//        val notification = NotificationCompat.Builder(this, "notification_channel")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("Todo has been created")
//            .setOngoing(true)
//            .addAction(R.drawable.ic_launcher_background, "Stop", stopPendingIntent)
////            .setDeleteIntent(stopPendingIntent)
//            .build()
//        startForeground(1, notification)
//    }


    private val NOTIFICATION_ID = 1
    private val NOTIFICATION_CHANNEL_ID = "create_channel"
    private val STOP_ACTION = "STOP_NOTIFICATION"

    private lateinit var notificationManager: NotificationManager
    private lateinit var stopIntent: PendingIntent
    private lateinit var stopReceiver: BroadcastReceiver


    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        stopIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(STOP_ACTION),
            PendingIntent.FLAG_IMMUTABLE
        )
        stopReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == STOP_ACTION) {
                    stopForeground(true)
                    stopSelf()
                }
            }
        }
        registerReceiver(stopReceiver, IntentFilter(STOP_ACTION))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Todo has been created")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(Notification.Action.Builder(null, "Stop", stopIntent).build())
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(stopReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    enum class Actions {
        START, STOP
    }

}