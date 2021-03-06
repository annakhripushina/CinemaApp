package com.example.cinema_app.presentation.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cinema_app.R
import com.example.cinema_app.dagger.CinemaApp
import com.example.cinema_app.dagger.module.viewmodel.CinemaViewModelFactory
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.presentation.view.cinemaList.CinemaListActivity
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.presentation.view.favourite.FavouriteActivity
import com.example.cinema_app.presentation.view.shedule.ScheduleActivity
import com.example.cinema_app.presentation.view.shedule.ScheduleViewModel
import com.example.cinema_app.receiver.ALARM_NOTIFICATION_SCHEDULE
import com.example.cinema_app.service.NOTIFICATION_FCM
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: CinemaViewModelFactory
    lateinit var viewModel: CinemaListViewModel
    lateinit var scheduleViewModel: ScheduleViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CinemaApp.appComponentViewModel.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CinemaListViewModel::class.java)
        viewModel.onGetCinemaList()
        scheduleViewModel =
            ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel::class.java)

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
                R.id.nav_Schedule -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerActivity, ScheduleActivity(), "scheduleActivity")
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

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val bundle = intent?.extras
        if (bundle != null) {
            if (bundle.containsKey(ALARM_NOTIFICATION_SCHEDULE)) {
                val cinema = bundle.getParcelable<Cinema>(ALARM_NOTIFICATION_SCHEDULE)
                cinema?.let { viewModel.onSetCinemaItem(it) }
                cinema?.let { scheduleViewModel.deleteScheduleCinema(it.originalId) }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerActivity, CinemaActivity(), "cinemaActivity")
                    .addToBackStack("cinemaActivity")
                    .commit()
            } else if (bundle.containsKey(NOTIFICATION_FCM)) {
                val cinema = bundle.getParcelable<Cinema>(NOTIFICATION_FCM)
                cinema?.let { viewModel.onSetCinemaItem(it) }
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