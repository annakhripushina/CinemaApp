package com.example.cinema_app.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.MainActivity


const val BUNDLE_NAME = "BUNDLE_NAME"
const val CINEMA_INFO = "CINEMA_INFO"
const val ALARM_NOTIFICATION_SCHEDULE = "ALARM_NOTIFICATION_SCHEDULE"

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID = "CHANNEL"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.getBundleExtra(BUNDLE_NAME)
        val cinema = bundle?.getParcelable<Cinema>(CINEMA_INFO)

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(ALARM_NOTIFICATION_SCHEDULE, cinema)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val name = context.getString(R.string.channelName)
        val description = context.getString(R.string.channelDescription)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notificationTitle))
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (cinema != null) {
            builder.setContentText(context.getString(R.string.notificationText) + cinema.title + "!")
        }

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        cinema?.let { notificationManagerCompat.notify(it.originalId, builder.build()) }
    }

}