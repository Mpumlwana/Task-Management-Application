package com.example.taskmanagementapp2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getLongExtra(NotificationHelper.EXTRA_REMINDER_ID, -1L)
        if (reminderId != -1L) {
            val notificationHelper = NotificationHelper(context)
            notificationHelper.showNotification(reminderId)
        }
    }
}