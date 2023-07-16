package com.mytodo.todo.data

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class RuningApp : Application() {

    override fun onCreate() {
        super.onCreate()
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "notification_channel",
                "Create Todo",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
//        }
    }
}