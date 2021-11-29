package com.example.cinema_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        val navigate: BottomNavigationView = findViewById(R.id.navigate)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerActivity, CinemaListActivity())
                .commit()
        }

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
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("cinemaActivity")?.isVisible != true) {
            DialogBack().show(supportFragmentManager, "dialog")
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}