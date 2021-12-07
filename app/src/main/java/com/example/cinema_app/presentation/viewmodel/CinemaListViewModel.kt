package com.example.cinema_app.presentation.viewmodel

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cinema_app.App
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.domain.CinemaDetailInteractor
import com.example.cinema_app.domain.CinemaFavouriteInteractor
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.utils.SingleLiveEvent

class CinemaListViewModel: ViewModel() {
    private val cinemaInteractor = App.instance.cinemaInteractor
    private val cinemaDetailInteractor: CinemaDetailInteractor = CinemaDetailInteractor()
    private val cinemaFavouriteInteractor: CinemaFavouriteInteractor = CinemaFavouriteInteractor()

    private val mCinemaList = MutableLiveData<ArrayList<Cinema>>()
    private lateinit var mCinemaItem: Cinema
    private var mFavouriteList: ArrayList<Cinema> = ArrayList()

    private var mComment: String = ""
    private var mHasLiked: Boolean = false
    private val mError: MutableLiveData<String> = SingleLiveEvent()
    private var mPage: Int = 1
    private var mTotalPages: Int = 1

    val cinemaList: LiveData<ArrayList<Cinema>>
        get() = mCinemaList

    val error: LiveData<String>
        get() = mError

    val cinemaItem: Cinema
        get() = mCinemaItem

    val hasLiked: Boolean
        get() = mHasLiked

    val comment: String
        get() = mComment

    val favouriteList: ArrayList<Cinema>
        get() = mFavouriteList

    fun onGetCinemaList() {
        cinemaInteractor.getCinema(mPage, mTotalPages, object : CinemaListInteractor.GetCinemaCallback {
            override fun onSuccess(cinemaList: ArrayList<Cinema>, page: Int, totalPages: Int) {
                mPage = page
                mTotalPages = totalPages
                mCinemaList.value = cinemaList
            }
            override fun onError(error: String) {
                mError.value = error
            }
        })
    }

    fun onSetFavouriteList(favouriteList: ArrayList<Cinema>){
        mFavouriteList = favouriteList
    }

    fun onSetCinemaItem(cinemaItem: Cinema){
        mCinemaItem = cinemaItem
    }

    fun onAddFavouriteItem(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema) {
        cinemaInteractor.onAddFavourite(favouriteList, cinemaItem)
    }

    fun onAddFavouritePosition(favouriteList: ArrayList<Cinema>, position: Int, cinemaItem: Cinema) {
        cinemaFavouriteInteractor.onAddFavouritePosition(favouriteList, position, cinemaItem)
    }

    fun onRemoveFavouriteItem(favouriteList: ArrayList<Cinema>, cinemaItem: Cinema){
        cinemaInteractor.onRemoveFavourite(favouriteList, cinemaItem)
    }

    fun onRemoveFavouritePosition(favouriteList: ArrayList<Cinema>, position: Int){
        cinemaFavouriteInteractor.onRemoveFavouritePosition(favouriteList, position)
    }

    fun onSetLikeClickListener(cinemaItem: Cinema, isChecked: Boolean){
        cinemaDetailInteractor.onSetLikeClickListener(cinemaItem, isChecked)
        mHasLiked = isChecked
    }

    fun onSetComment(input: EditText){
        mComment = input.text.toString()
    }


}

