package com.example.cinema_app.dagger

import android.app.Application
import com.example.cinema_app.dagger.component.AppComponent
import com.example.cinema_app.dagger.component.DaggerAppComponent
import com.example.cinema_app.dagger.component.DaggerViewModelComponent
import com.example.cinema_app.dagger.component.ViewModelComponent
import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule

class CinemaApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
        lateinit var appComponentViewModel: ViewModelComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .roomDbModule(RoomDbModule(this))
            .retrofitModule(RetrofitModule(this))
            .build()
        appComponentViewModel = DaggerViewModelComponent.builder()
            .appComponent(appComponent)
            .build()

    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }

}
