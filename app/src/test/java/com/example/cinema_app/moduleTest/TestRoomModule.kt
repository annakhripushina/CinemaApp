package com.example.cinema_app.moduleTest

import android.content.Context
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.data.room.CinemaRoomDB
import io.mockk.mockk
import org.robolectric.RuntimeEnvironment.application

class TestRoomModule : RoomDbModule(application){
    override fun getCinemaDAO(cinemaRoomDB: CinemaRoomDB): CinemaDao = mockk()
    override fun getRoomDbInstance(): CinemaRoomDB = mockk()
    override fun provideAppContext(): Context = mockk()
}