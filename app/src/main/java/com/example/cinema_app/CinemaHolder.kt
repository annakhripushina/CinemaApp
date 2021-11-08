package com.example.cinema_app

import android.annotation.SuppressLint

object CinemaHolder{
    @SuppressLint("ResourceAsColor")
    val movies = arrayListOf<Cinema>(
    Cinema(1, R.string.img1TextView, R.string.img1Description, R.drawable.img_1, R.color.black,false, false),
    Cinema(2, R.string.img2TextView, R.string.img2Description, R.drawable.img_2, R.color.black,false, false),
    Cinema(3, R.string.img3TextView, R.string.img3Description, R.drawable.img_3, R.color.black, false, false))
}