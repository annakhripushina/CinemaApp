package com.example.cinema_app.dagger.module

import android.app.Application
import android.content.Context
import com.example.cinema_app.data.CinemaRepository
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.ICinemaRepository
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.data.room.CinemaRoomDB
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
open class RoomDbModule(val application: Application) {
    @Singleton
    @Provides
    open fun getCinemaDAO(cinemaRoomDB: CinemaRoomDB): CinemaDao {
        return cinemaRoomDB.getCinemaDao()
    }

    @Singleton
    @Provides
    open fun getRoomDbInstance(): CinemaRoomDB {
        return CinemaRoomDB.getDatabase(provideAppContext())
    }

    @Singleton
    @Provides
    open fun provideAppContext(): Context {
        return application.applicationContext
    }

    @Reusable
    @Provides
    open fun provideCinemaRepository(
        cinemaDao: CinemaDao,
        cinemaService: CinemaService
    ): ICinemaRepository =
        CinemaRepository(cinemaDao, cinemaService)

}