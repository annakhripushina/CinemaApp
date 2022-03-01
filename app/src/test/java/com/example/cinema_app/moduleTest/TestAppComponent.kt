package com.example.cinema_app.moduleTest

import com.example.cinema_app.dagger.component.AppComponent
import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.domain.ICinemaListInteractor
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDbModule::class, RetrofitModule::class])
interface TestAppComponent : AppComponent {
    override fun getCinemaDAO(): CinemaDao
    override fun provideCinemaService(): CinemaService
    override fun provideCinemaListInteractor(): ICinemaListInteractor
}