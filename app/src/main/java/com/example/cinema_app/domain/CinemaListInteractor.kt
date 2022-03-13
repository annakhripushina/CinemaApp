package com.example.cinema_app.domain

import com.example.cinema_app.data.ICinemaRepository
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.model.toDomainModel
import com.example.cinema_app.service.FirebaseRemoteConfigService
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import dagger.Reusable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@Reusable
class CinemaListInteractor @Inject constructor(private val cinemaRepository: ICinemaRepository) :
    FirebaseRemoteConfigService, ICinemaListInteractor {
    private var items = ArrayList<Cinema>()
    private lateinit var remoteConfigKey: FirebaseRemoteConfig

    override lateinit var cinemaItem: Cinema

    override var hasLiked: Boolean = false

    override fun onSetCinemaItem(cinema: Cinema) {
        cinemaItem = cinema
    }

    override fun onSetHasLiked(like: Boolean) {
        hasLiked = like
    }

    override fun getLatestCinema(): Single<Cinema> {
        return cinemaRepository.getLatestCinema()
            .subscribeOn(Schedulers.io())
            .map { it.toDomainModel() }
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getCinema(page: Int): Single<List<Cinema>> {
        remoteConfigKey = getRemoteConfig()
        val cinemaTag = remoteConfigKey["Category"].asString()
        items.clear()
        return cinemaRepository.getCinemaPage(cinemaTag, page)
            .subscribeOn(Schedulers.io())
            .map {
                it.results.map { it.toDomainModel() }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

}



