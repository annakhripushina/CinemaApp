package com.example.cinema_app.domain

import com.example.cinema_app.data.entity.Cinema
import io.reactivex.rxjava3.core.Single

interface ICinemaListInteractor {
    var cinemaItem: Cinema

    var hasLiked: Boolean

    fun onSetCinemaItem(cinema: Cinema)

    fun onSetHasLiked(like: Boolean)

    fun getLatestCinema(): Single<Cinema>

    fun getCinema(page: Int): Single<List<Cinema>>
}