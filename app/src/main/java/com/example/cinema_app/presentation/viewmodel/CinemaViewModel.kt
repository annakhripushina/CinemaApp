package com.example.cinema_app.presentation.viewmodel

import android.app.Application
import android.graphics.Color
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.App
import com.example.cinema_app.data.CinemaRepository
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.entity.LikedCinema
import com.example.cinema_app.data.entity.ScheduleCinema
import com.example.cinema_app.data.model.CinemaModel
import com.example.cinema_app.data.room.CinemaRoomDB
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.utils.SingleLiveEvent
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

class CinemaViewModel(application: Application) : AndroidViewModel(application) {
    private val cinemaInteractor = App.instance.cinemaInteractor
    private lateinit var mCinemaItem: Cinema
    private var mAllCinema = ArrayList<Cinema>()
    private var mComment: String = ""
    private var mHasLiked: Boolean = false
    private val mError: MutableLiveData<String> = SingleLiveEvent()
    private var mPage: Int = 1
    private var mTotalPages: Int = 1
    private val repository: CinemaRepository
    var allCinema: LiveData<List<Cinema>>
    var allFavouriteCinema: LiveData<List<Cinema>>
    var allLikedCinema: LiveData<List<LikedCinema>>
    var allScheduleCinema: LiveData<List<Cinema>>
    var allSchedule: LiveData<List<ScheduleCinema>>

    val error: LiveData<String>
        get() = mError

    val cinemaItem: Cinema
        get() = mCinemaItem

    val hasLiked: Boolean
        get() = mHasLiked

    val comment: String
        get() = mComment

    init {
        val cinemaDao = CinemaRoomDB.getDatabase(application).getCinemaDao()
        repository = CinemaRepository(cinemaDao)
        allCinema = repository.allCinema
        allFavouriteCinema = repository.allFavouriteCinema
        allLikedCinema = repository.allLikedCinema
        allScheduleCinema = repository.allScheduleCinema
        allSchedule = repository.allSchedule
        onGetCinemaList()
    }

    fun onGetCinemaListPage() {
        onGetCinemaList(mPage + 1)
    }

    private val errorLiveData = MutableLiveData<String>()
    fun onGetCinemaList(page: Int = 1) {
        cinemaInteractor.getCinema(page)
            .subscribe(object : SingleObserver<List<Cinema>> {
                override fun onSubscribe(d: Disposable) {}

                override fun onSuccess(list: List<Cinema>) {
                    if (list.isEmpty()) {
                        Log.d("TAG", "getMovies. Got empty data")
                    } else {
                        viewModelScope.launch(Dispatchers.IO) {
                            mPage = page
                            if (page == 1) deleteAll()
                            insertCinemaList(list as ArrayList<Cinema>)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    errorLiveData.postValue(e.message)
                }
            })
    }

    fun onSetCinemaItem(cinemaItem: Cinema) {
        mCinemaItem = cinemaItem
    }

    fun onAddFavouriteCinema(cinemaItem: Cinema) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavouriteCinema(FavouriteCinema(cinemaItem.originalId))
        }

    fun onRemoveFavouriteCinema(cinemaItem: Cinema) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavouriteCinema(cinemaItem.originalId)
        }

    fun onSetLikeClickListener(cinemaItem: Cinema, isChecked: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            if (isChecked)
                repository.insertLikedCinema(LikedCinema(cinemaItem.originalId))
            else
                repository.deleteLikedCinema(cinemaItem.originalId)
        }

    fun onSetComment(input: EditText) {
        mComment = input.text.toString()
    }

    fun insertCinemaList(list: ArrayList<Cinema>?) = viewModelScope.launch(Dispatchers.IO) {
        list?.forEach {
            repository.insertCinema(it)
        }
    }

    fun insertScheduleCinema(cinemaItem: ScheduleCinema) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertScheduleCinema(cinemaItem)
        }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun deleteScheduleCinema(cinemaOriginalId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteScheduleCinema(cinemaOriginalId)
    }

    fun updateTitleColor(titleColor: Int, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTitleColor(titleColor, id)
    }

    fun updateDateViewed(dateViewed: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateDateViewed(dateViewed, id)
    }

    fun getLike(cinemaItem: Cinema) {
        mHasLiked = allLikedCinema.value!!.contains(LikedCinema(cinemaItem.originalId))
    }

}

