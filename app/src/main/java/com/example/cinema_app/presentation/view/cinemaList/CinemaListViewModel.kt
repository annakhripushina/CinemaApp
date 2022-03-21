package com.example.cinema_app.presentation.view.cinemaList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.data.ICinemaRepository
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.domain.ICinemaListInteractor
import com.example.cinema_app.utils.SingleLiveEvent
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.launch
import javax.inject.Inject

class CinemaListViewModel
@Inject constructor(
    private val cinemaInteractor: ICinemaListInteractor,
    private val cinemaRepository: ICinemaRepository
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

//    init {
//        onGetCinemaList()
//    }

    fun onGetAllCinema(): LiveData<List<Cinema>> {
        cinemaRepository.getAll()
            .subscribe(
                { value -> mAllCinema.postValue(value) },
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
                    viewModelScope.launch {
                        if (page == 1) cinemaRepository.deleteAll()

                        list?.forEach {
                            cinemaRepository.insertCinema(it)
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
        viewModelScope.launch {
            cinemaRepository.insertFavouriteCinema(FavouriteCinema(cinemaItem.originalId))
        }

    fun onRemoveFavouriteCinema(cinemaItem: Cinema) =
        viewModelScope.launch {
            cinemaRepository.deleteFavouriteCinema(cinemaItem.originalId)
        }

    fun updateTitleColor(titleColor: Int, id: Int) = viewModelScope.launch {
        cinemaRepository.updateTitleColor(titleColor, id)
    }

    fun onSearchCinema(title: String): LiveData<List<Cinema>> {
        if (title.isNotEmpty()) {
            cinemaRepository.searchCinema(title)
                .subscribe(
                    { value -> mSearchedCinema.postValue(value) },
                    { error -> mSearchedCinema.postValue(listOf()) })
            return searchedCinema
        } else {
            return onGetAllCinema()
        }
    }

}