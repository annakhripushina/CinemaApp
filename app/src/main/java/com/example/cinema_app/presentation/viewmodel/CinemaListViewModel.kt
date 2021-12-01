package com.example.cinema_app.presentation.viewmodel

import android.content.Intent
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.cinema_app.data.CinemaHolder
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.domain.CinemaDetailInteractor
import com.example.cinema_app.domain.CinemaListInteractor

class CinemaListViewModel: ViewModel() {
    private val cinemaListInteractor: CinemaListInteractor = CinemaListInteractor()
    private val cinemaDetailInteractor: CinemaDetailInteractor = CinemaDetailInteractor()

    private var mComment: String = ""
    private var mHasLiked: Boolean = false
    private lateinit var mCinemaItem: Cinema
    private var mCinemaList: ArrayList<Cinema> = ArrayList()
    private var mFavouriteList: ArrayList<Cinema> = ArrayList()

    val cinemaList: ArrayList<Cinema>
        get() = mCinemaList

    val cinemaItem: Cinema
        get() = mCinemaItem

    val hasLiked: Boolean
        get() = mHasLiked

    val comment: String
        get() = mComment

    val favouriteList: ArrayList<Cinema>
        get() = mFavouriteList

    fun onGetCinemaList() {
        mCinemaList = cinemaListInteractor.getCinemaList()
    }

    fun onSetFavouriteList(favouriteList: ArrayList<Cinema>){
        mFavouriteList = favouriteList
    }

    fun onAddFavouriteItem(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema) {
        cinemaListInteractor.onAddFavourite(favouriteList, cinemaItem)
    }

    fun onAddFavouritePosition(favouriteList: ArrayList<Cinema>, position: Int, cinemaItem: Cinema) {
        favouriteList.add(position, cinemaItem)
    }

    fun onRemoveFavouriteItem(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema){
        cinemaListInteractor.onRemoveFavourite(favouriteList, cinemaItem)
    }

    fun onRemoveFavouritePosition(favouriteList: ArrayList<Cinema>, position: Int){
        favouriteList.removeAt(position)
    }




    fun onSetCinemaItem(cinemaItem: Cinema){
        mCinemaItem = cinemaItem
    }

    fun onSetLikeClickListener(cinemaId: Int, isChecked: Boolean){
        cinemaDetailInteractor.onSetLikeClickListener(cinemaId, isChecked)
        mHasLiked = isChecked
    }

    fun onTextChangedListener(input: EditText){
        mComment = input.text.toString()
    }

}

