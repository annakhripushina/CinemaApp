package com.example.cinema_app.data

import com.example.cinema_app.data.model.CinemaListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CinemaService {
    @GET("movie/popular")
    fun getCinemaPage(@Query("page") page: Int): Call<CinemaListModel>
}