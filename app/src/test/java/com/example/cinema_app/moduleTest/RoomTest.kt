package com.example.cinema_app.moduleTest

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.model.toDomainModel
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.getValueBlocking
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)

class RoomTest {
    @Inject
    lateinit var cinemaService: CinemaService
    @Inject
    lateinit var cinemaDao: CinemaDao

    //private lateinit var instrumentationContext: Context

    @Before
    fun setUp(){
        val component = DaggerTestAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .roomDbModule(RoomDbModule(getApplicationContext())) //<-- Application?
            .build()
        component.into(this)
        //instrumentationContext = InstrumentationRegistry.getInstrumentation().context

    }

    @Test
    fun insertTest(){
        Assert.assertNotNull(cinemaService)
        val result = cinemaService.getCinemaPage("popular", 1).blockingGet().results.map{ it.toDomainModel()}

        Assert.assertEquals(20, result.size)

        Assert.assertNotNull(cinemaDao)
        cinemaDao.deleteAll()

        for (it in result){
            cinemaDao.insertCinema(it)
        }

        val resultDB = LiveDataReactiveStreams.fromPublisher(cinemaDao.getAll()).getValueBlocking()
        if (resultDB != null) {
            Assert.assertEquals(20, resultDB.size)
        }
    }

}