package com.example.cinema_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.cinema_app.data.entity.Cinema
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

    private val originalId = 524434
    private val title = "Eternals"
    private val description =
        "The Eternals are a team of ancient aliens who have been living on Earth in secret for thousands of years."
    private val image = "https://image.tmdb.org/t/p/w500//bcCBq9N1EMo3daNIjWJ8kYvrQm6.jpg"
    private var titleColor = -16777216
    private val dateViewed = ""
    private val cinema = Cinema(originalId, title, description, image, titleColor, dateViewed)

    @Test
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
        cinemaDao.updateTitleColor(0, cinema.id!!)
        var cinemaDB = LiveDataReactiveStreams.fromPublisher(cinemaDao.searchCinema(cinema.title))
            .getValueBlocking()
        assertEquals(1, cinemaDB?.size)
        assertEquals(0, cinemaDB?.get(0)?.titleColor)
    }

}