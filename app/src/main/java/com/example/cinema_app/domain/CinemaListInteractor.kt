package com.example.cinema_app.domain

import android.graphics.Color
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.model.CinemaListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CinemaListInteractor(private val cinemaService: CinemaService) {
    private var items = ArrayList<Cinema>()

    fun getCinema(page: Int, callback: GetCinemaCallback) {
        var totalPages = 1

        cinemaService.getCinemaPage(page).enqueue(object : Callback<CinemaListModel> {
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
                                        Color.BLACK
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
        fun onError(error: String)
    }

}