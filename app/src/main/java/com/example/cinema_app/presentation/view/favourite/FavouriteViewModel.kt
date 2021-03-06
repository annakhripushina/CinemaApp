package com.example.cinema_app.presentation.view.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.data.ICinemaRepository
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.FavouriteCinema
import com.example.cinema_app.domain.ICinemaListInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel
@Inject constructor(
    private val cinemaInteractor: ICinemaListInteractor,
    private val cinemaRepository: ICinemaRepository
) : ViewModel() {
    var allFavouriteCinema = MutableLiveData<List<Cinema>>()

    val cinemaItem: Cinema
        get() = cinemaInteractor.cinemaItem

    fun onSetCinemaItem(cinemaItem: Cinema) {
        cinemaInteractor.onSetCinemaItem(cinemaItem)
    }

    fun onGetFavouriteCinema(): LiveData<List<Cinema>> {
        cinemaRepository.getFavouriteCinema()
            .subscribe({ value -> allFavouriteCinema.postValue(value) },
                { error ->
                    allFavouriteCinema.postValue(listOf())
                })
        return allFavouriteCinema
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

}