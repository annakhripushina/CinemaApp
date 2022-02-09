package com.example.cinema_app.dagger.module

import android.app.Application
import android.content.Context
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.data.room.CinemaRoomDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDbModule(val application: Application) {
    @Singleton
    @Provides
    fun getCinemaDAO(cinemaRoomDB: CinemaRoomDB): CinemaDao {
        return cinemaRoomDB.getCinemaDao()
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(): CinemaRoomDB {
        return CinemaRoomDB.getDatabase(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

}