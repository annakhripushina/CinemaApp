package com.example.cinema_app.data

import com.example.cinema_app.data.model.CinemaListModel
import com.example.cinema_app.data.model.CinemaModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CinemaService {
    @GET("movie/{category}")
    fun getCinemaPage(
        @Path("category") category: String,
        @Query("page") page: Int
    ): Call<CinemaListModel>

    @GET("movie/latest")
    fun getLatestCinema(): Call<CinemaModel>
}
