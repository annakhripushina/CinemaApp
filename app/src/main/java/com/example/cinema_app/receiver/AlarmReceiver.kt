package com.example.cinema_app.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
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
        val cinema = bundle?.getParcelable<Cinema>(CINEMA_INFO)!!
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(ALARM_NOTIFICATION_SCHEDULE, cinema)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel name"
            val description = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Напоминание о просмотре")
            .setContentText("Приготовили попкорн? Пора смотреть фильм " + cinema.title + "!")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(cinema.original_id, builder.build())
    }

}