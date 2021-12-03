package com.example.cinema_app.domain

import android.graphics.Color
import android.util.Log
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.CinemaListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CinemaListInteractor(private val cinemaService: CinemaService) {
    var items = ArrayList<Cinema>()

    fun getCinema(callback: GetRepoCallback) {
        cinemaService.getCinemaPage().enqueue(object : Callback<CinemaListModel> {
            override fun onResponse(call: Call<CinemaListModel>, response: Response<CinemaListModel>) {
                if (response.isSuccessful) {

                    items.clear()
                    if (response.isSuccessful) {
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
                                Log.d("TAG_ITEMS", items[0].title)
                            }
                    }

                    callback.onSuccess(items)
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CinemaListModel>, t: Throwable) {
                Log.d("NENEN ", t.message.toString())
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

    interface GetRepoCallback {
        fun onSuccess(repos: ArrayList<Cinema>)
        fun onError(error: String)
    }


}