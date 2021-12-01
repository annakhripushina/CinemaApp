package com.example.cinema_app.domain

import com.example.cinema_app.data.CinemaHolder
import com.example.cinema_app.data.entity.Cinema

class CinemaListInteractor() {
    fun getCinemaList(): ArrayList<Cinema> {
        return CinemaHolder.cinemaList
    }

    fun onAddFavourite(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema) {
        favouriteList.add(cinemaItem)
    }

    fun onRemoveFavourite(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema){
        favouriteList.remove(cinemaItem)
    }


}