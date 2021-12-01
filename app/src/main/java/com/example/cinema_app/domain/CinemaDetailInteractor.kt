package com.example.cinema_app.domain

import com.example.cinema_app.data.CinemaHolder

class CinemaDetailInteractor {
    fun onSetLikeClickListener(cinemaId: Int, isChecked: Boolean){
        CinemaHolder.cinemaList[cinemaId].hasLiked = isChecked
    }
}