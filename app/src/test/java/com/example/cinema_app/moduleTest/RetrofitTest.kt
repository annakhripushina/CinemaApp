package com.example.cinema_app.moduleTest

import android.app.Application
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinema_app.dagger.module.RetrofitModule
import com.example.cinema_app.dagger.module.RoomDbModule
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.model.CinemaListModel
import com.example.cinema_app.data.model.CinemaModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import java.util.function.Consumer
import javax.inject.Inject
import javax.inject.Named

@RunWith(RobolectricTestRunner::class)
class RetrofitTest {
    @Inject
    lateinit var cinemaService: CinemaService

    @Before
    fun setUp(){
        val component = DaggerTestAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .roomDbModule(RoomDbModule(Application()))
            .build()
        component.into(this)
    }

    //Интеграционные тесты
    @Test
    fun getCinemaPageTest(){
        assertNotNull(cinemaService)
        val result = cinemaService.getCinemaPage("popular", 1).blockingGet().results.size
        assertEquals(20, result)
    }

    @Test
    fun getLatestCinemaTest(){
        assertNotNull(cinemaService)
        val result = cinemaService.getLatestCinema()
        assertNotNull(result)
    }

}