package com.example.cinema_app.domain

import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.model.toDomainModel
import com.example.cinema_app.service.FirebaseRemoteConfigService
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CinemaListInteractor(private val cinemaService: CinemaService) : FirebaseRemoteConfigService {
    private var items = ArrayList<Cinema>()
    private lateinit var remoteConfigKey: FirebaseRemoteConfig

    fun getLatestCinema(): Single<Cinema> {
        return cinemaService.getLatestCinema()
            .subscribeOn(Schedulers.io())
            .map { it.toDomainModel() }
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun getCinema(page: Int): Single<List<Cinema>> {
        remoteConfigKey = getRemoteConfig()
        val cinemaTag = remoteConfigKey["Category"].asString()
        items.clear()
        return cinemaService.getCinemaPage(cinemaTag, page)
            .subscribeOn(Schedulers.io())
            .map {
                it.results.map { it.toDomainModel() }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

}



