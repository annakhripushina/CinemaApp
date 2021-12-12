package com.example.cinema_app.presentation.viewmodel

import android.app.Application
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.App
import com.example.cinema_app.data.CinemaRepository
import com.example.cinema_app.data.CinemaRoomDB
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.domain.CinemaDetailInteractor
import com.example.cinema_app.domain.CinemaFavouriteInteractor
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class CinemaListViewModel(application: Application) : AndroidViewModel(application) {
    private val cinemaInteractor = App.instance.cinemaInteractor

    private val cinemaDetailInteractor: CinemaDetailInteractor = CinemaDetailInteractor()
    private val cinemaFavouriteInteractor: CinemaFavouriteInteractor = CinemaFavouriteInteractor()

    private lateinit var mCinemaItem: Cinema

    private var mComment: String = ""
    private var mHasLiked: Boolean = false
    private val mError: MutableLiveData<String> = SingleLiveEvent()
    private var mPage: Int = 1
    private var mTotalPages: Int = 1

    private var likedCinema: ArrayList<LikedCinema> = ArrayList()

    val error: LiveData<String>
        get() = mError

    val cinemaItem: Cinema
        get() = mCinemaItem

    val hasLiked: Boolean
        get() = mHasLiked

    var hasLikedDB: Boolean = false

    val comment: String
        get() = mComment

    fun onGetCinemaListPages() {
        if ((mPage < mTotalPages)||(mTotalPages == 1)) {
            Toast.makeText(getApplication(), "Загрузка...", Toast.LENGTH_SHORT).show()
            onGetCinemaList(mPage + 1)
        }
    }

    fun onGetCinemaList(page: Int = 1) {
        cinemaInteractor.getCinema(page, object : CinemaListInteractor.GetCinemaCallback {
            override fun onSuccess(cinemaList: ArrayList<Cinema>, page: Int, totalPages: Int) {
                mPage = page
                mTotalPages = totalPages
                if (page == 1) {
                    deleteAll()
                }
                insert(cinemaList)
            }

            override fun onError(error: String) {
                mError.value = error
            }
        })
    }

    fun onSetCinemaItem(cinemaItem: Cinema) {
        mCinemaItem = cinemaItem
    }

    fun onAddFavouriteItem(cinemaItem: Cinema) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavourite(FavouriteCinema(cinemaItem.original_id))
    }

    fun onRemoveFavouriteItem(cinemaItem: Cinema) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavouriteItem(cinemaItem.original_id)
    }

    fun onSetLikeClickListener(cinemaItem: Cinema, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO)  {
        //cinemaDetailInteractor.onSetLikeClickListener(cinemaItem, isChecked)
        //cinemaItem.id?.let { repository.updateLike(isChecked, it) }
        if (isChecked)
            repository.insertLikedCinema(LikedCinema(cinemaItem.original_id))
        else
            repository.deleteLikedItem(cinemaItem.original_id)


    }

    fun onSetComment(input: EditText) {
        mComment = input.text.toString()
    }

    private val repository: CinemaRepository

    var allCinema: LiveData<List<Cinema>>
    var allFavouriteList: LiveData<List<Cinema>>

    init {
        val wordsDao = CinemaRoomDB.getDatabase(application, viewModelScope).getCinemaDao()
        repository = CinemaRepository(wordsDao)
        allCinema = repository.allCinema
        allFavouriteList = repository.favouriteCinema

        onGetCinemaList()
    }

    fun insert(list: ArrayList<Cinema>?) = viewModelScope.launch(Dispatchers.IO) {
        list?.forEach {
            repository.insert(it)
            Unit
        }
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun updateTitleColor(titleColor: Int, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTitleColor(titleColor, id)
    }

    fun getLikedCinema(cinemaItem: Cinema) {

    }

    fun hasLiked(cinemaItem: Cinema) = viewModelScope.launch(Dispatchers.IO) {
        likedCinema = repository.getLikedCinema(cinemaItem.original_id) as ArrayList<LikedCinema>

       // println(likedCinema.await())
    }

    fun getLike(cinemaItem: Cinema){
        hasLiked(cinemaItem)
        mHasLiked = likedCinema.isNotEmpty()
    }

}

