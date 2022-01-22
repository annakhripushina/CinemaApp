package com.example.cinema_app.domain

import android.graphics.Color
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.model.CinemaListModel
import com.example.cinema_app.data.model.CinemaModel
import com.example.cinema_app.service.FirebaseRemoteConfigService
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CinemaListInteractor(private val cinemaService: CinemaService) : FirebaseRemoteConfigService {
    private var items = ArrayList<Cinema>()
    private lateinit var cinemaLatestItem: Cinema
    private lateinit var FRConfig: FirebaseRemoteConfig

    fun getLatestCinema(callback: GetCinemaCallback) {
        cinemaService.getLatestCinema().enqueue(object : Callback<CinemaModel> {
            override fun onResponse(
                call: Call<CinemaModel>,
                response: Response<CinemaModel>
            ) {
                if (response.isSuccessful) {
                    cinemaLatestItem = response.body()?.let {
                        Cinema(
                            it.id,
                            it.original_title,
                            it.overview,
                            "https://image.tmdb.org/t/p/w500/" + it.poster_path,
                            Color.BLACK,
                            ""
                        )
                    }!!
                    callback.onSuccessLatest(cinemaLatestItem)
                }
            }

            override fun onFailure(call: Call<CinemaModel>, t: Throwable) {
            }
        })
    }

    fun getCinema(page: Int, callback: GetCinemaCallback) {
        var totalPages = 1

        FRConfig = getRemoteConfig()
        var cinemaTag = FRConfig["Category"].asString()

        cinemaService.getCinemaPage(cinemaTag, page).enqueue(object : Callback<CinemaListModel> {
            override fun onResponse(
                call: Call<CinemaListModel>,
                response: Response<CinemaListModel>
            ) {
                if (response.isSuccessful) {
                    items.clear()
                    if (response.isSuccessful) {
                        totalPages = response.body()?.totalPages!!
                        response.body()?.results
                            ?.forEach {
                                items.add(
                                    Cinema(
                                        it.id,
                                        it.original_title,
                                        it.overview,
                                        "https://image.tmdb.org/t/p/w500/" + it.poster_path,
                                        Color.BLACK,
                                        ""
                                    )
                                )
                            }
                    }

                    callback.onSuccess(items, page, totalPages)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CinemaListModel>, t: Throwable) {
                callback.onError("Network error probably...")
            }
        })
    }

    interface GetCinemaCallback {
        fun onSuccess(cinemaList: ArrayList<Cinema>, page: Int, totalPages: Int)
        fun onSuccessLatest(cinemaItem: Cinema)
        fun onError(error: String)
    }
}



