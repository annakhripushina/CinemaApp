package com.example.cinema_app.presentation.view.detail

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.domain.ICinemaListInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CinemaDetailViewModel
@Inject constructor(private val cinemaInteractor: ICinemaListInteractor, private val cinemaDao: CinemaDao) : ViewModel() {
    private var mComment: String = ""
    private var mHasLiked: Boolean = false

    var allLikedCinema = MutableLiveData<List<LikedCinema>>()

    val cinemaItem: Cinema
        get() = cinemaInteractor.cinemaItem

    val hasLiked: Boolean
        get() = mHasLiked

//    @Inject
//    lateinit var cinemaDao: CinemaDao

    fun onGetLikedCinema(): LiveData<List<LikedCinema>> {
        cinemaDao.getLikedCinema()
            .subscribe({ value -> allLikedCinema.postValue(value) },
                { error ->
                    allLikedCinema.postValue(listOf())
                })
        return allLikedCinema
    }

    fun onSetLikeClickListener(cinemaItem: Cinema, isChecked: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            if (isChecked)
                cinemaDao.insertLikedCinema(LikedCinema(cinemaItem.originalId))
            else
                cinemaDao.deleteLikedCinema(cinemaItem.originalId)
        }

    fun onSetComment(input: EditText) {
        mComment = input.text.toString()
    }

    fun getLike(cinemaItem: Cinema) {
        mHasLiked = allLikedCinema.value!!.contains(LikedCinema(cinemaItem.originalId))
        cinemaInteractor.onSetHasLiked(mHasLiked)
    }

}