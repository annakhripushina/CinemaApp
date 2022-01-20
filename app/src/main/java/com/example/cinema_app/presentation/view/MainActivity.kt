package com.example.cinema_app.presentation.view

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.cinemaList.CinemaListActivity
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.presentation.view.favourite.FavouriteActivity
import com.example.cinema_app.presentation.viewmodel.CinemaViewModel
import com.example.cinema_app.presentation.viewmodel.CinemaViewModelFactory
import com.example.cinema_app.receiver.ALARM_NOTIFICATION_SCHEDULE
import com.example.cinema_app.service.NOTIFICATION_FCM
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private val cinemaViewModelFactory by lazy { CinemaViewModelFactory(application) }
    private val cinemaViewModel by lazy {
        ViewModelProvider(this, cinemaViewModelFactory).get(
            CinemaViewModel::class.java
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerActivity, CinemaListActivity())
                .commit()
        }

        val navigate: BottomNavigationView = findViewById(R.id.navigate)

        navigate.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_cinema -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, CinemaListActivity(), "cinemaListActivity")
                        .commit()
                }
                R.id.nav_favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, FavouriteActivity(), "favouriteActivity")
                        .commit()
                }
            }
            true
        }
        navigate.setOnItemReselectedListener {
            if (supportFragmentManager.findFragmentByTag("cinemaActivity")?.isVisible == true) {
                supportFragmentManager.popBackStack()
            }
            true
        }

        onNewIntent(intent)

 /*       createChannel(
            "FCM_Notification",
            "Movie recommendations"
        )

        subscribeTopic()*/

    }

 /*   private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )
                // TODO: Step 2.6 disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Hello"

            val notificationManager = this.getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    private fun subscribeTopic() {
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var msg = "getString(R.string.message_subscribed)"
                if (!task.isSuccessful) {
                    msg = "getString(R.string.message_subscribe_failed)"
                }
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        // [END subscribe_topics]
    }
*/
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val bundle = intent?.extras
        if (bundle != null) {
            if (bundle.containsKey(ALARM_NOTIFICATION_SCHEDULE)) {
                val cinema = bundle.getParcelable<Cinema>(ALARM_NOTIFICATION_SCHEDULE)
                cinemaViewModel.onSetCinemaItem(cinema!!)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerActivity, CinemaActivity(), "cinemaActivity")
                    .addToBackStack("cinemaActivity")
                    .commit()
            }else if (bundle.containsKey(NOTIFICATION_FCM)) {
                val cinema = bundle.getParcelable<Cinema>(NOTIFICATION_FCM)
                cinemaViewModel.onSetCinemaItem(cinema!!)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerActivity, CinemaActivity(), "cinemaActivity")
                    .addToBackStack("cinemaActivity")
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("cinemaActivity")?.isVisible != true) {
            DialogBack().show(supportFragmentManager, "dialog")
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}