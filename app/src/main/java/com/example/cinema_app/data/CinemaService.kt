package com.example.cinema_app.data

import com.example.cinema_app.data.entity.CinemaListModel
import com.example.cinema_app.data.entity.CinemaModel
import retrofit2.Call
import retrofit2.http.*


interface CinemaService {
    @GET("movie/popular")
    fun getCinema(): Call<ArrayList<CinemaModel>>

    @GET("movie/popular")
    fun getCinemaPage(): Call<CinemaListModel>

    @POST("movie/popular")
    fun setCinemaById(@Query("id") id: String): Call<ArrayList<CinemaModel>>

}