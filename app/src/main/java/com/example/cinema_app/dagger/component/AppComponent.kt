package com.example.cinema_app.dagger.component

import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.room.CinemaDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDbModule::class/*, CinemaViewModelModule::class*/, RetrofitModule::class])
interface AppComponent {
    //    fun inject(mainActivity: MainActivity)
//    fun inject(cinemaListFragment: CinemaListActivity)
//    fun inject(cinemaDetailFragment: CinemaActivity)
//    fun inject(favouriteFragment: FavouriteActivity)
//    fun inject(scheduleFragment: ScheduleActivity)
    fun getCinemaDAO(): CinemaDao

    fun provideCinemaService(): CinemaService

}