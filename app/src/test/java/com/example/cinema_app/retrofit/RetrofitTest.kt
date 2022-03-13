package com.example.cinema_app.retrofit

import android.app.Application
import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.retrofit.dagger.DaggerTestAppComponent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
class RetrofitTest {
    @Inject
    lateinit var cinemaService: CinemaService

    @Before
    fun setUp() {
        val component = DaggerTestAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .roomDbModule(RoomDbModule(Application()))
            .build()
        component.into(this)
    }

    //Интеграционные тесты
    @Test
    fun getCinemaPageTest() {
        assertNotNull(cinemaService)
        val result = cinemaService.getCinemaPage("popular", 1).blockingGet().results.size
        assertEquals(20, result)
    }

    @Test
    fun getLatestCinemaTest() {
        assertNotNull(cinemaService)
        val result = cinemaService.getLatestCinema()
        assertNotNull(result)
    }

}