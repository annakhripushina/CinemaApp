package com.example.cinema_app.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.ScheduleCinema
import com.example.cinema_app.data.room.CinemaDao

class CinemaRepository(private val cinemaDao: CinemaDao) {
    val allCinema: LiveData<List<Cinema>> = cinemaDao.getAll()
    val allFavouriteCinema: LiveData<List<Cinema>> = cinemaDao.getFavouriteCinema()
    val allLikedCinema: LiveData<List<LikedCinema>> = cinemaDao.getLikedCinema()
    val allScheduleCinema: LiveData<List<Cinema>> = cinemaDao.getScheduleCinema()
    val allSchedule: LiveData<List<ScheduleCinema>> = cinemaDao.getSchedule()

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
