package com.example.cinema_app.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.cinema_app.receiver.AlarmReceiver
import com.example.cinema_app.receiver.BUNDLE_NAME
import com.example.cinema_app.receiver.MOVIE_INFO
import com.example.cinema_app.data.entity.Cinema

class AlarmService(private val context: Context) {

    private val alarmManager by lazy { context.getSystemService(Context.ALARM_SERVICE) as AlarmManager }

    companion object {
        val TAG = AlarmService::class.toString()
    }

    fun setExactAlarm(cinema: Cinema, time: Long, requestCode: Int) {
        setAlarm(time, getPendingIntent(getIntent().apply {
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_INFO, cinema)
            putExtra(BUNDLE_NAME, bundle)
        }, requestCode))
    }

    fun stopAlarms(requestCode: Int) {
        Log.d(TAG, "stopAlarm for $requestCode")
        getPendingIntent(getIntent(), requestCode).cancel()
    }

    @SuppressLint("NewApi")
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