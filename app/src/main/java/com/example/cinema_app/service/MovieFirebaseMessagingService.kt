package com.example.cinema_app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinema_app.App

import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.presentation.view.MainActivity
import com.example.cinema_app.presentation.viewmodel.CinemaViewModel
import com.example.cinema_app.presentation.viewmodel.CinemaViewModelFactory
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*

const val NOTIFICATION_FCM = "ALARM_NOTIFICATION_SCHEDULE"
const val ANY_MOVIE_RANDOM_ID = 1

class MovieFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        val TAG = MovieFirebaseMessagingService::class.toString()
    }

    //private val movieService = MovieApi.movieService

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage)
    }

    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        Log.d(TAG, "FCM token: $token")
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val notificationChannelId = "FCM_Notification"
        val cinemaInteractor = App.instance.cinemaInteractor
        val messageText = remoteMessage.data["text"]
        var movie : Cinema? = null
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        //TODO: вынести в функцию
        //runBlocking {
          //  val job = GlobalScope.launch {
            cinemaInteractor.getCinema(1, object : CinemaListInteractor.GetCinemaCallback {
                override fun onSuccess(cinemaList: ArrayList<Cinema>, page: Int, totalPages: Int) {
                }
                override fun onSuccessLatest(cinemaItem: Cinema) {
                    movie = cinemaItem
                }
                override fun onError(error: String) {
                }
            })
           // }
       //     job.join()
        //}


        Thread.sleep(2000L)

        mainActivityIntent.putExtra(NOTIFICATION_FCM, movie)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Movie recommendations"
            val description = "The most interesting film to watch"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(notificationChannelId, name, importance)
            channel.description = description
            val notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, notificationChannelId)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(messageText)
            .setContentText("${movie?.title}\n ${movie?.description}")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(this)
        movie?.let { notificationManager.notify(it.original_id, builder.build()) }
    }
}