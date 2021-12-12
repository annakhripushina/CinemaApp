package com.example.cinema_app.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema

class CinemaRepository(private val cinemaDao: CinemaDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allCinema: LiveData<List<Cinema>> = cinemaDao.getAll()

    val favouriteCinema: LiveData<List<Cinema>> = cinemaDao.getFavouriteCinema()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cinemaItem: Cinema) {
        cinemaDao.insert(cinemaItem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFavourite(cinemaItem: FavouriteCinema) {
        cinemaDao.insertFavourite(cinemaItem)
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
    suspend fun deleteFavouriteItem(cinemaOriginalId: Int) {
        cinemaDao.deleteFavouriteItem(cinemaOriginalId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteLikedItem(cinemaOriginalId: Int) {
        cinemaDao.deleteLikedItem(cinemaOriginalId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTitleColor(titleColor: Int, id: Int) {
        cinemaDao.updateTitleColor(titleColor, id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getLikedCinema(cinemaOriginalId: Int): List<LikedCinema> {
        return cinemaDao.getLikedCinema(cinemaOriginalId)
    }

}
