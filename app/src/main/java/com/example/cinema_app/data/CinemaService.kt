package com.example.cinema_app.data

import com.example.cinema_app.data.entity.CinemaListModel
import com.example.cinema_app.data.entity.CinemaModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CinemaService {
    @GET("movie/popular")
    fun getCinema(): Call<ArrayList<CinemaModel>>

    @GET("movie/popular")
    fun getCinemaPage(@Query("page") page: Int): Call<CinemaListModel>

}