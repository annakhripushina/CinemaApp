package com.example.cinema_app.dagger.component

import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.ICinemaRepository
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.domain.ICinemaListInteractor
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDbModule::class, RetrofitModule::class])
interface AppComponent {
    fun getCinemaDAO(): CinemaDao
    fun provideCinemaService(): CinemaService
    fun provideCinemaListInteractor(): ICinemaListInteractor
    fun provideCinemaRepository(): ICinemaRepository
}