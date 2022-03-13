package com.example.cinema_app.data

import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.ScheduleCinema
import com.example.cinema_app.data.model.CinemaListModel
import com.example.cinema_app.data.model.CinemaModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface ICinemaRepository {
    fun getAll(): Flowable<List<Cinema>>

    fun getLikedCinema(): Flowable<List<LikedCinema>>

    fun getFavouriteCinema(): Flowable<List<Cinema>>

    fun getScheduleCinema(): Flowable<List<Cinema>>

    fun getSchedule(): Flowable<List<ScheduleCinema>>

    fun searchCinema(title: String): Flowable<List<Cinema>>

    fun insertCinema(cinemaItem: Cinema)

    fun insertFavouriteCinema(cinemaItem: FavouriteCinema)

    fun insertLikedCinema(cinemaItem: LikedCinema)

    fun deleteAll()

    fun deleteFavouriteCinema(cinemaOriginalId: Int)

    fun deleteLikedCinema(cinemaOriginalId: Int)

    fun updateTitleColor(titleColor: Int, id: Int)

    fun updateDateViewed(dateViewed: String, cinemaOriginalId: Int)

    fun insertScheduleCinema(cinemaItem: ScheduleCinema)

    fun deleteScheduleCinema(cinemaOriginalId: Int)

    fun getLatestCinema(): Single<CinemaModel>

    fun getCinemaPage(category: String, page: Int): Single<CinemaListModel>

}
