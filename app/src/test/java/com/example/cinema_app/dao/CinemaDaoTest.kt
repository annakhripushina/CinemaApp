package com.example.cinema_app.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.cinema_app.CinemaItem
import com.example.cinema_app.getValueBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CinemaDaoTest : RoomDB() {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val cinema = CinemaItem.cinema

    @Test
    fun testt(){

    }

    /*@Test
    fun insertTest() {
        cinemaDao.insertCinema(cinema)
        val cinemaDB = LiveDataReactiveStreams.fromPublisher(cinemaDao.getAll()).getValueBlocking()
        assertEquals(1, cinemaDB?.size)
    }

    @Test
    fun deleteTest() {
        cinemaDao.insertCinema(cinema)
        var cinemaDB = LiveDataReactiveStreams.fromPublisher(cinemaDao.getAll()).getValueBlocking()
        assertEquals(1, cinemaDB?.size)
        cinemaDao.deleteAll()
        cinemaDB = LiveDataReactiveStreams.fromPublisher(cinemaDao.getAll()).getValueBlocking()
        assertEquals(0, cinemaDB?.size)
    }

    @Test
    fun updateTest() {
        cinema.id = 1
        cinemaDao.insertCinema(cinema)
        cinema.id?.let { cinemaDao.updateTitleColor(0, it) }
        val cinemaDB = LiveDataReactiveStreams.fromPublisher(cinemaDao.searchCinema(cinema.title))
            .getValueBlocking()
        assertEquals(1, cinemaDB?.size)
        assertEquals(0, cinemaDB?.get(0)?.titleColor)
    }*/

}