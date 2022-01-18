package com.example.cinema_app.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.WatchCinema
import com.example.cinema_app.data.room.CinemaDao

class CinemaRepository(private val cinemaDao: CinemaDao) {
    val allCinema: LiveData<List<Cinema>> = cinemaDao.getAll()
    val allFavouriteCinema: LiveData<List<Cinema>> = cinemaDao.getFavouriteCinema()
    val allLikedCinema: LiveData<List<LikedCinema>> = cinemaDao.getLikedCinema()

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
    suspend fun insertWatchCinema(cinemaItem: WatchCinema) {
        cinemaDao.insertWatchCinema(cinemaItem)
    }

}
