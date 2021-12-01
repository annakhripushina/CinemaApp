package com.example.cinema_app.domain

import com.example.cinema_app.data.entity.Cinema

class CinemaFavouriteInteractor {

    fun onAddFavouritePosition(favouriteList: ArrayList<Cinema>, position: Int, cinemaItem: Cinema) {
        favouriteList.add(position, cinemaItem)
    }

    fun onRemoveFavouritePosition(favouriteList: ArrayList<Cinema>, position: Int){
        favouriteList.removeAt(position)
    }

}