package com.example.cinema_app.moduleTest

import android.app.Application
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.CinemaService
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

//Попытки протестировать Retrofit

class RetrofitTest {
    @Inject
    lateinit var cinemaService: CinemaService
    //private lateinit var instrumentationContext: Context

    @Before
    fun setUp(){
        //instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        val component = DaggerTestAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .roomDbModule(RoomDbModule(Application()))
            .build()
    }

    @Test
    fun testt(){
        assertNotNull(cinemaService)
    }
}