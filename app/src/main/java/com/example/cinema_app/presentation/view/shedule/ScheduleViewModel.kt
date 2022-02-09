package com.example.cinema_app.presentation.view.shedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.entity.ScheduleCinema
import com.example.cinema_app.data.room.CinemaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleViewModel @Inject constructor() : ViewModel() {
    var allScheduleCinema = MutableLiveData<List<Cinema>>()
    var allSchedule = MutableLiveData<List<ScheduleCinema>>()

    @Inject
    lateinit var cinemaDao: CinemaDao

    fun onGetSchedule(): LiveData<List<ScheduleCinema>> {
        cinemaDao.getSchedule()
            .subscribe({ value -> allSchedule.postValue(value) },
                { error ->
                    allSchedule.postValue(listOf())
                })
        return allSchedule
    }

    fun onGetScheduleCinema(): LiveData<List<Cinema>> {
        cinemaDao.getScheduleCinema()
            .subscribe({ value -> allScheduleCinema.postValue(value) },
                { error ->
                    allScheduleCinema.postValue(listOf())
                })
        return allScheduleCinema
    }

    fun insertScheduleCinema(cinemaItem: ScheduleCinema) =
        viewModelScope.launch(Dispatchers.IO) {
            cinemaDao.insertScheduleCinema(cinemaItem)
        }

    fun deleteScheduleCinema(cinemaOriginalId: Int) = viewModelScope.launch(Dispatchers.IO) {
        cinemaDao.deleteScheduleCinema(cinemaOriginalId)
    }

    fun updateDateViewed(dateViewed: String, id: Int) = viewModelScope.launch(Dispatchers.IO) {
        cinemaDao.updateDateViewed(dateViewed, id)
    }

}