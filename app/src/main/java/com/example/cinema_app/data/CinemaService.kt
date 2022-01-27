package com.example.cinema_app.data

import com.example.cinema_app.data.model.CinemaListModel
import com.example.cinema_app.data.model.CinemaModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CinemaService {
    @GET("movie/{category}")
    fun getCinemaPage(
        @Path("category") category: String,
        @Query("page") page: Int
    ): Single<CinemaListModel>

    @GET("movie/latest")
    fun getLatestCinema(): Single<CinemaModel>
}
