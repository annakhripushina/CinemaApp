package com.example.cinema_app.presentation.viewmodel

import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema_app.App
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.domain.CinemaDetailInteractor
import com.example.cinema_app.domain.CinemaFavouriteInteractor
import com.example.cinema_app.domain.CinemaListInteractor

class CinemaListViewModel: ViewModel() {

    init {
        Log.d("RepoListView", this.toString())
    }

    private val cinemaInteractor = App.instance.cinemaInteractor

    private val mRepos = MutableLiveData<ArrayList<Cinema>>()
    private val mError = MutableLiveData<String>()
    private val mSelectedRepoUrl = MutableLiveData<String>()

    val repos: LiveData<ArrayList<Cinema>>
        get() = mRepos

    val error: LiveData<String>
        get() = mError

    fun onGetDataClick() {
        cinemaInteractor.getCinema(object : CinemaListInteractor.GetRepoCallback {
            override fun onSuccess(repos: ArrayList<Cinema>) {
                mRepos.value = repos
            }

            override fun onError(error: String) {
                mError.value = error
            }
        })
    }

    private val cinemaDetailInteractor: CinemaDetailInteractor = CinemaDetailInteractor()
    private val cinemaFavouriteInteractor: CinemaFavouriteInteractor = CinemaFavouriteInteractor()


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

