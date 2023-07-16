package com.mytodo.todo.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.ServiceCompat.stopForeground

class StopServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == "STOP") {
            // Stop your notification service here
            context.stopService(Intent(context, NotificationService::class.java))
        }


    }
}