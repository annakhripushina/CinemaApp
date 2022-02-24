package com.example.cinema_app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.data.room.CinemaRoomDB
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
abstract class RoomDB {
    private lateinit var cinemaRoomDB: CinemaRoomDB
    protected lateinit var cinemaDao: CinemaDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        cinemaRoomDB = Room.inMemoryDatabaseBuilder(
            context, CinemaRoomDB::class.java
        ).allowMainThreadQueries().build()
        cinemaDao = cinemaRoomDB.getCinemaDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cinemaRoomDB.close()
    }
}