package com.example.cinema_app.presentation.view.shedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.data.ICinemaRepository
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.ScheduleCinema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
    private val cinemaRepository: ICinemaRepository
) : ViewModel() {
    var allScheduleCinema = MutableLiveData<List<Cinema>>()
    var allSchedule = MutableLiveData<List<ScheduleCinema>>()

    fun onGetSchedule(): LiveData<List<ScheduleCinema>> {
        cinemaRepository.getSchedule()
            .subscribe({ value -> allSchedule.postValue(value) },
                { error ->
                    allSchedule.postValue(listOf())
                })
        return allSchedule
    }

    fun onGetScheduleCinema(): LiveData<List<Cinema>> {
        cinemaRepository.getScheduleCinema()
            .subscribe({ value -> allScheduleCinema.postValue(value) },
                { error ->
                    allScheduleCinema.postValue(listOf())
                })
        return allScheduleCinema
    }

    fun insertScheduleCinema(cinemaItem: ScheduleCinema) =
        viewModelScope.launch(Dispatchers.IO) {
            cinemaRepository.insertScheduleCinema(cinemaItem)
        }

    fun deleteScheduleCinema(cinemaOriginalId: Int) = viewModelScope.launch(Dispatchers.IO) {
        cinemaRepository.deleteScheduleCinema(cinemaOriginalId)
    }

    fun updateDateViewed(dateViewed: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        cinemaRepository.updateDateViewed(dateViewed, id)
    }

}