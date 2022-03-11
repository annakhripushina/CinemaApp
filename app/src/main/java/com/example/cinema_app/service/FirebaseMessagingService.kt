package com.example.cinema_app.service

//import com.example.cinema_app.dagger.component.DaggerFirebaseComponent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.cinema_app.R
import com.example.cinema_app.dagger.CinemaApp
import com.example.cinema_app.dagger.component.DaggerFirebaseComponent
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.presentation.view.MainActivity
import com.example.cinema_app.utils.InternalLog
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

const val NOTIFICATION_FCM = "NOTIFICATION_FCM"

class FirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var cinemaInteractor: CinemaListInteractor

    companion object {
        val TAG = FirebaseMessagingService::class.toString()
        const val NOTIFICATION_CHANNEL_ID = "FCM_CHANNEL"
    }

    override fun onCreate() {
        DaggerFirebaseComponent.builder()
            .appComponent((application as CinemaApp).getAppComponent())
            .build()
            .inject(this)
        super.onCreate()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage)
    }

    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        InternalLog.d(TAG, "Refreshed token: $token")
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val notificationChannelId = NOTIFICATION_CHANNEL_ID
        val messageText = remoteMessage.data[getString(R.string.messageTextFCM)]

        cinemaInteractor.getLatestCinema()
            .subscribe(object : SingleObserver<Cinema> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(cinema: Cinema) {
                    val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)

                    mainActivityIntent.putExtra(NOTIFICATION_FCM, cinema)

                    val pendingIntent = PendingIntent.getActivity(
                        applicationContext,
                        0,
                        mainActivityIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    val name = getString(R.string.channelNameFCM)
                    val description = getString(R.string.channelDescriptionFCM)
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(notificationChannelId, name, importance)
                    channel.description = description
                    val notificationManager =
                        applicationContext.getSystemService(NotificationManager::class.java)
                    notificationManager.createNotificationChannel(channel)

                    val builder =
                        NotificationCompat.Builder(applicationContext, notificationChannelId)
                            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setContentTitle(messageText)
                            .setContentText("${cinema?.title}\n ${cinema?.description}")
                            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                            .setPriority(NotificationCompat.PRIORITY_LOW)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                    val notificationManagerCompat =
                        NotificationManagerCompat.from(applicationContext)
                    cinema?.let { notificationManagerCompat.notify(it.originalId, builder.build()) }
                }

                override fun onError(e: Throwable) {}
            })
    }

}