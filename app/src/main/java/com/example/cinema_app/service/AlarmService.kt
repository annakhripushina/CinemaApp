package com.example.cinema_app.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.receiver.AlarmReceiver
import com.example.cinema_app.receiver.BUNDLE_NAME
import com.example.cinema_app.receiver.CINEMA_INFO

class AlarmService(private val context: Context) {
    private val alarmManager by lazy { context.getSystemService(Context.ALARM_SERVICE) as AlarmManager }

    fun setExactAlarm(cinema: Cinema, time: Long, requestCode: Int) {
        setAlarm(time, getPendingIntent(getIntent().apply {
            val bundle = Bundle()
            bundle.putParcelable(CINEMA_INFO, cinema)
            putExtra(BUNDLE_NAME, bundle)
        }, requestCode))
    }

    private fun setAlarm(time: Long, pendingIntent: PendingIntent) {
        alarmManager.let {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)
        }
    }

    private fun getIntent(): Intent = Intent(context, AlarmReceiver::class.java)

    private fun getPendingIntent(intent: Intent, requestCode: Int) =
        PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
}