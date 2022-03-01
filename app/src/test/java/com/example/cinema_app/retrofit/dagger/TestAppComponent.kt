package com.example.cinema_app.retrofit.dagger

import com.example.cinema_app.dagger.component.AppComponent
import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.retrofit.RetrofitTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDbModule::class, RetrofitModule::class])
interface TestAppComponent : AppComponent {
//    override fun getCinemaDAO(): CinemaDao
//    override fun provideCinemaService(): CinemaService
//    override fun provideCinemaListInteractor(): ICinemaListInteractor
      fun into(retrofitTest: RetrofitTest)
}