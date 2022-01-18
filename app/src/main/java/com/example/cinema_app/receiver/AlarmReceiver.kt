package com.example.cinema_app.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.MainActivity
import com.example.cinema_app.presentation.view.detail.CinemaActivity


const val BUNDLE_NAME = "myBundle"
const val MOVIE_INFO = "MOVIE_INFO"
const val ALARM_NOTIFICATION_SCHEDULE = "ALARM_NOTIFICATION_SCHEDULE"

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "channel"
        val TAG = AlarmReceiver::class.toString()
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")
        Log.d(TAG, "action = " + intent.action)
        showNotification(context, intent)
    }

    private fun showNotification(context: Context, intent: Intent) {
        val bundle = intent.getBundleExtra(BUNDLE_NAME)
        val movie = bundle?.getParcelable<Cinema>(MOVIE_INFO)!!
        val mainActivityIntent = Intent(context, MainActivity::class.java)
        //val mainActivityIntent = Intent(context, CinemaActivity::class.java)
        mainActivityIntent.putExtra(ALARM_NOTIFICATION_SCHEDULE, movie)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

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
            .setContentTitle("Your should to watch it!")
            .setContentText(movie.title)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(movie.original_id, builder.build())
    }
}