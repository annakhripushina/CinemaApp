package com.example.cinema_app.dagger.component

import com.example.cinema_app.dagger.module.viewmodel.CinemaViewModelModule
import com.example.cinema_app.presentation.view.MainActivity
import com.example.cinema_app.presentation.view.cinemaList.CinemaListActivity
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.presentation.view.favourite.FavouriteActivity
import com.example.cinema_app.presentation.view.shedule.ScheduleActivity
import dagger.Component
import javax.inject.Scope

@ViewModelScope
@Component(dependencies = [AppComponent::class], modules = [CinemaViewModelModule::class])
interface ViewModelComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(cinemaListFragment: CinemaListActivity)
    fun inject(cinemaDetailFragment: CinemaActivity)
    fun inject(favouriteFragment: FavouriteActivity)
    fun inject(scheduleFragment: ScheduleActivity)
}

@Scope
annotation class ViewModelScope