package com.example.cinema_app.presentation.view.cinemaList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.utils.SingleLiveEvent
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CinemaListViewModel
@Inject constructor(private val cinemaInteractor: CinemaListInteractor) : ViewModel() {
    private var mComment: String = ""
    private var mHasLiked: Boolean = false
    private val mAllCinema: MutableLiveData<List<Cinema>> = SingleLiveEvent()
    private val mError: MutableLiveData<String> = SingleLiveEvent()
    private var mPage: Int = 1

    var allCinema = MutableLiveData<List<Cinema>>()
        get() = mAllCinema

    val error: MutableLiveData<String>
        get() = mError

    val hasLiked: Boolean
        get() = mHasLiked

    val comment: String
        get() = mComment

    @Inject
    lateinit var cinemaDao: CinemaDao

    init {
        onGetCinemaList()
    }

    fun onGetAllCinema(): LiveData<List<Cinema>> {
        cinemaDao.getAll()
            .subscribe({ value -> mAllCinema.postValue(value) },
                { error ->
                    mAllCinema.postValue(listOf())
                })
        return allCinema
    }

    fun onSearchCinema(title: String): LiveData<List<Cinema>> {
        if (title.isNotEmpty()) {
            //mAllCinema.postValue(listOf())
            cinemaDao.searchCinema(title)
                .subscribe({ value -> mAllCinema.postValue(value) },
                    { error ->
                        mAllCinema.postValue(listOf())
                    })
            return allCinema
        } else
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
                            cinemaDao.insertCinema(it)
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    error.postValue("Network error probably...")
                }
            })
    }

    fun onSetCinemaItem(cinemaItem: Cinema) {
        cinemaInteractor.onSetCinemaItem(cinemaItem)
    }

    fun onAddFavouriteCinema(cinemaItem: Cinema) =
        viewModelScope.launch(Dispatchers.IO) {
            cinemaDao.insertFavouriteCinema(FavouriteCinema(cinemaItem.originalId))
        }

    fun onRemoveFavouriteCinema(cinemaItem: Cinema) =
        viewModelScope.launch(Dispatchers.IO) {
            cinemaDao.deleteFavouriteCinema(cinemaItem.originalId)
        }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        cinemaDao.deleteAll()
    }

    fun updateTitleColor(titleColor: Int, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        cinemaDao.updateTitleColor(titleColor, id)
    }

}