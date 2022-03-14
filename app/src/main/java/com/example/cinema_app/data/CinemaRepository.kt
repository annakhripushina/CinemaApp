package com.example.cinema_app.data

import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.ScheduleCinema
import com.example.cinema_app.data.model.CinemaListModel
import com.example.cinema_app.data.model.CinemaModel
import com.example.cinema_app.data.room.CinemaDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CinemaRepository @Inject constructor(
    private val cinemaDao: CinemaDao,
    private val cinemaService: CinemaService
) : ICinemaRepository {

    override fun getAll(): Flowable<List<Cinema>> {
        return cinemaDao.getAll()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getLikedCinema(): Flowable<List<LikedCinema>> {
        return cinemaDao.getLikedCinema()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFavouriteCinema(): Flowable<List<Cinema>> {
        return cinemaDao.getFavouriteCinema()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getScheduleCinema(): Flowable<List<Cinema>> {
        return cinemaDao.getScheduleCinema()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSchedule(): Flowable<List<ScheduleCinema>> {
        return cinemaDao.getSchedule()
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun searchCinema(title: String): Flowable<List<Cinema>> {
        return cinemaDao.searchCinema(title)
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun insertCinema(cinemaItem: Cinema) {
        cinemaDao.insertCinema(cinemaItem)
    }

    override suspend fun insertFavouriteCinema(cinemaItem: FavouriteCinema) {
        cinemaDao.insertFavouriteCinema(cinemaItem)
    }

    override suspend fun insertLikedCinema(cinemaItem: LikedCinema) {
        cinemaDao.insertLikedCinema(cinemaItem)
    }

    override suspend fun deleteAll() {
        cinemaDao.deleteAll()
    }

    override suspend fun deleteFavouriteCinema(cinemaOriginalId: Int) {
        cinemaDao.deleteFavouriteCinema(cinemaOriginalId)
    }

    override suspend fun deleteLikedCinema(cinemaOriginalId: Int) {
        cinemaDao.deleteLikedCinema(cinemaOriginalId)
    }

    override suspend fun updateTitleColor(titleColor: Int, id: Int) {
        cinemaDao.updateTitleColor(titleColor, id)
    }

    override suspend fun updateDateViewed(dateViewed: String, cinemaOriginalId: Int) {
        cinemaDao.updateDateViewed(dateViewed, cinemaOriginalId)
    }

    override suspend fun insertScheduleCinema(cinemaItem: ScheduleCinema) {
        cinemaDao.insertScheduleCinema(cinemaItem)
    }

    override suspend fun deleteScheduleCinema(cinemaOriginalId: Int) {
        cinemaDao.deleteScheduleCinema(cinemaOriginalId)
    }

    override fun getLatestCinema(): Single<CinemaModel> {
        return cinemaService.getLatestCinema()
    }

    override fun getCinemaPage(category: String, page: Int): Single<CinemaListModel> {
        return cinemaService.getCinemaPage(category, page)
    }

}
