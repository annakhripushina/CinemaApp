package com.example.cinema_app.domain

import com.example.cinema_app.data.entity.Cinema

class CinemaDetailInteractor {
    fun onSetLikeClickListener(cinemaItem: Cinema, isChecked: Boolean){
        cinemaItem.hasLiked = isChecked
    }
}