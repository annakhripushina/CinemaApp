package com.example.cinema_app.presentation.viewmodel

import android.app.Application
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
import com.example.cinema_app.data.room.CinemaRoomDB
import com.example.cinema_app.utils.SingleLiveEvent
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CinemaViewModel(application: Application) : AndroidViewModel(application) {
    private val cinemaInteractor = App.instance.cinemaInteractor
    private lateinit var mCinemaItem: Cinema
    private var mComment: String = ""
    private var mHasLiked: Boolean = false
    private val mError: MutableLiveData<String> = SingleLiveEvent()
    private var mPage: Int = 1
    private val repository: CinemaRepository
    var allCinema = MutableLiveData<List<Cinema>>()
    var allFavouriteCinema = MutableLiveData<List<Cinema>>()
    var allLikedCinema= MutableLiveData<List<LikedCinema>>()
    var allScheduleCinema = MutableLiveData<List<Cinema>>()
    var allSchedule= MutableLiveData<List<ScheduleCinema>>()

    val error: MutableLiveData<String>
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
        onGetCinemaList()
    }

    fun onGetAllCinema(): LiveData<List<Cinema>>{
        repository.getAllCinema()
            .subscribe({value -> allCinema.postValue(value) },
                { error ->
                    allCinema.postValue(listOf())
                })
        return allCinema
    }

    fun onGetLikedCinema(): LiveData<List<LikedCinema>>{
        repository.getAllLikedCinema()
            .subscribe({value -> allLikedCinema.postValue(value) },
                { error ->
                    allLikedCinema.postValue(listOf())
                })
        return allLikedCinema
    }

    fun onGetSchedule(): LiveData<List<ScheduleCinema>>{
        repository.getAllSchedule()
            .subscribe({value -> allSchedule.postValue(value) },
                { error ->
                    allSchedule.postValue(listOf())
                })
        return allSchedule
    }

    fun onGetScheduleCinema(): LiveData<List<Cinema>>{
        repository.getAllScheduleCinema()
            .subscribe({value -> allScheduleCinema.postValue(value) },
                { error ->
                    allScheduleCinema.postValue(listOf())
                })
        return allScheduleCinema
    }

    fun onGetFavouriteCinema(): LiveData<List<Cinema>>{
        repository.getAllFavouriteCinema()
            .subscribe({value -> allFavouriteCinema.postValue(value) },
                { error ->
                    allFavouriteCinema.postValue(listOf())
                })
        return allFavouriteCinema
    }

    fun onSearchCinema(title: String): LiveData<List<Cinema>>{
        if (title.isNotEmpty()) {
            repository.searchCinema(title)
                .subscribe({
                        value -> allCinema.postValue(value) },
                    { error ->
                        allCinema.postValue(listOf())
                    })
            return allCinema
        }
        else
            return onGetAllCinema()
    }

    fun onGetCinemaListPage() {
        onGetCinemaList(mPage + 1)
    }

    fun onGetCinemaList(page: Int = 1) {
        mPage = page
        cinemaInteractor.getCinema(page)
            .subscribe(object : SingleObserver<List<Cinema>> {
                override fun onSubscribe(d: Disposable) {}

                override fun onSuccess(list: List<Cinema>) {
                    viewModelScope.launch(Dispatchers.IO) {
                        if (page == 1) deleteAll()
                        list?.forEach {
                            repository.insertCinema(it)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    error.postValue("Network error probably...")
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

