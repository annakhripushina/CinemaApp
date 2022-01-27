package com.example.cinema_app.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.ScheduleCinema
import com.example.cinema_app.data.room.CinemaDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class CinemaRepository(private val cinemaDao: CinemaDao) {
    //val allCinema: LiveData<List<Cinema>> = cinemaDao.getAll()
    //val allLikedCinema: LiveData<List<LikedCinema>> = cinemaDao.getLikedCinema()

    fun getAllCinema(): Flowable<List<Cinema>> {
        return cinemaDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllLikedCinema(): Flowable<List<LikedCinema>> {
        return cinemaDao.getLikedCinema()
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllFavouriteCinema(): Flowable<List<Cinema>> {
        return cinemaDao.getFavouriteCinema()
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllScheduleCinema(): Flowable<List<Cinema>> {
        return cinemaDao.getScheduleCinema()
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAllSchedule(): Flowable<List<ScheduleCinema>> {
        return cinemaDao.getSchedule()
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchCinema(title: String): Flowable<List<Cinema>> {
        return cinemaDao.searchCinema(title)
            .observeOn(AndroidSchedulers.mainThread())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCinema(cinemaItem: Cinema) {
        cinemaDao.insertCinema(cinemaItem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFavouriteCinema(cinemaItem: FavouriteCinema) {
        cinemaDao.insertFavouriteCinema(cinemaItem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertLikedCinema(cinemaItem: LikedCinema) {
        cinemaDao.insertLikedCinema(cinemaItem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        cinemaDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteFavouriteCinema(cinemaOriginalId: Int) {
        cinemaDao.deleteFavouriteCinema(cinemaOriginalId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteLikedCinema(cinemaOriginalId: Int) {
        cinemaDao.deleteLikedCinema(cinemaOriginalId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTitleColor(titleColor: Int, id: Int) {
        cinemaDao.updateTitleColor(titleColor, id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateDateViewed(dateViewed: String, cinemaOriginalId: Int) {
        cinemaDao.updateDateViewed(dateViewed, cinemaOriginalId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertScheduleCinema(cinemaItem: ScheduleCinema) {
        cinemaDao.insertScheduleCinema(cinemaItem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteScheduleCinema(cinemaOriginalId: Int) {
        cinemaDao.deleteScheduleCinema(cinemaOriginalId)
    }

}
