package com.example.cinema_app.data

import android.annotation.SuppressLint
import android.graphics.Color
import com.example.cinema_app.R
import com.example.cinema_app.data.entity.Cinema

object CinemaHolder {
    @SuppressLint("ResourceAsColor")
    var cinemaList = arrayListOf<Cinema>(
        Cinema(
            0,
            R.string.img1TextView,
            R.string.img1Description,
            R.drawable.img_1,
            Color.BLACK,
            false
        ),
        Cinema(
            1,
            R.string.img2TextView,
            R.string.img2Description,
            R.drawable.img_2,
            Color.BLACK,
            false
        ),
        Cinema(
            2,
            R.string.img3TextView,
            R.string.img3Description,
            R.drawable.img_3,
            Color.BLACK,
            false
        ),
        Cinema(
            3,
            R.string.img1TextView,
            R.string.img1Description,
            R.drawable.img_1,
            Color.BLACK,
            false
        ),
        Cinema(
            4,
            R.string.img2TextView,
            R.string.img2Description,
            R.drawable.img_2,
            Color.BLACK,
            false
        ),
        Cinema(
            5,
            R.string.img3TextView,
            R.string.img3Description,
            R.drawable.img_3,
            Color.BLACK,
            false
        )
    )
}