package com.example.cinema_app.presentation.view.cinemaList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.domain.ICinemaListInteractor
import com.example.cinema_app.utils.SingleLiveEvent
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CinemaListViewModel
@Inject constructor(
    private val cinemaInteractor: ICinemaListInteractor,
    private val cinemaDao: CinemaDao
) : ViewModel() {
    private var mComment: String = ""
    private var mHasLiked: Boolean = false
    private val mAllCinema: MutableLiveData<List<Cinema>> = SingleLiveEvent()
    private val mError: MutableLiveData<String> = SingleLiveEvent()
    private var mPage: Int = 1
    private val mSearchedCinema: MutableLiveData<List<Cinema>> = SingleLiveEvent()

    var allCinema = MutableLiveData<List<Cinema>>()
        get() = mAllCinema

    var searchedCinema = MutableLiveData<List<Cinema>>()
        get() = mSearchedCinema

    val error: MutableLiveData<String>
        get() = mError

    val hasLiked: Boolean
        get() = mHasLiked

    val comment: String
        get() = mComment

    fun onGetAllCinema(): LiveData<List<Cinema>> {
        cinemaDao.getAll()
            .subscribe({ value -> mAllCinema.postValue(value) },
                { error ->
                    mAllCinema.postValue(listOf())
                })
        return allCinema
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
                        if (page == 1) cinemaDao.deleteAll()

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

    fun onGetHasLiked() {
        mHasLiked = cinemaInteractor.hasLiked
    }

    fun onAddFavouriteCinema(cinemaItem: Cinema) =
        viewModelScope.launch(Dispatchers.IO) {
            cinemaDao.insertFavouriteCinema(FavouriteCinema(cinemaItem.originalId))
        }

    fun onRemoveFavouriteCinema(cinemaItem: Cinema) =
        viewModelScope.launch(Dispatchers.IO) {
            cinemaDao.deleteFavouriteCinema(cinemaItem.originalId)
        }

    fun updateTitleColor(titleColor: Int, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        cinemaDao.updateTitleColor(titleColor, id)
    }

    //    fun onSearchCinema(title: String): LiveData<List<Cinema>> {
//        if (title.isNotEmpty()) {
//            cinemaDao.searchCinema(title)
//                .subscribe(
//                    { value -> mSearchedCinema.postValue(value) },
//                    { error -> mSearchedCinema.postValue(listOf()) })
//            return searchedCinema
//        } else {
//            return onGetAllCinema()
//        }
//    }
    fun onSearchCinema(title: String): LiveData<List<Cinema>> {
        if (title.isNotEmpty()) {
            viewModelScope.launch {
                cinemaDao.searchCinema(title)
                    .subscribe(
                        { value -> mSearchedCinema.postValue(value) },
                        { error -> mSearchedCinema.postValue(listOf()) })
            }
            return searchedCinema

        } else {
            return onGetAllCinema()
        }
    }

}