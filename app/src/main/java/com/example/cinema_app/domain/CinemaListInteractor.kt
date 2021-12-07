package com.example.cinema_app.domain

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.CinemaListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CinemaListInteractor(private val cinemaService: CinemaService) {
    var items = ArrayList<Cinema>()
    //var totalPage: Int = 2
    //var page: Int = 1

    fun getCinema(page: Int, totalPages: Int, callback: GetCinemaCallback) {
       /* if (items.isNotEmpty() && page<totalPage){
            page += 1
        }*/
        var vTotalPages: Int = totalPages
        var vPage: Int = page
        if (items.isNotEmpty() && page < totalPages){
            vPage += 1
        }
        cinemaService.getCinemaPage(vPage).enqueue(object : Callback<CinemaListModel> {
            override fun onResponse(call: Call<CinemaListModel>, response: Response<CinemaListModel>) {
                if (response.isSuccessful) {
                    //items.clear()
                    if (response.isSuccessful) {
                        vTotalPages = response.body()?.totalPages!!
                        response.body()?.results
                            ?.forEach {
                                items.add(
                                    Cinema(
                                        it.id,
                                        it.original_title,
                                        it.overview,
                                        "https://image.tmdb.org/t/p/w500/" + it.poster_path,
                                        Color.BLACK,
                                        false
                                    )
                                )
                            }
                    }

                    callback.onSuccess(items, vPage, vTotalPages)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CinemaListModel>, t: Throwable) {
                callback.onError("Network error probably...")
            }
        })

    }

    fun onAddFavourite(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema) {
        favouriteList.add(cinemaItem)
    }

    fun onRemoveFavourite(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema){
        favouriteList.remove(cinemaItem)
    }

    interface GetCinemaCallback {
        fun onSuccess(cinemaList: ArrayList<Cinema>, page: Int, totalPages: Int)
        fun onError(error: String)
    }


}