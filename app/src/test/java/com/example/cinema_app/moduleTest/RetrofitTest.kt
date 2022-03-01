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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject
import javax.inject.Named

//Попытки протестировать Retrofit

@RunWith(RobolectricTestRunner::class)
class RetrofitTest {
    @Inject
    lateinit var cinemaService: CinemaService

    private val cinemaModel: ArrayList<CinemaModel> = ArrayList()

    @Before
    fun setUp(){
        cinemaModel.add(
            CinemaModel(
                true, "", listOf(1), 1, "", "",
                "", 1.1, "", "", "", false, 1.1, 1
            )
        )

        val component = DaggerTestAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .roomDbModule(RoomDbModule(Application()))
            .build()
        component.into(this)
    }

    @Test
    fun testt(){
        assertNotNull(cinemaService)
        //Mockito.`when`(cinemaService.getCinemaPage("popular", 1)).thenReturn(Single.just(CinemaListModel(1,1,1, cinemaModel)))
//        every { cinemaService.getCinemaPage("popular", 1) } returns Single.just(CinemaListModel(1,1,1, cinemaModel))
//        val result = cinemaService.getCinemaPage("popular", 1)
//        result.test()
//            .assertValue(CinemaListModel(1,1,1, cinemaModel))

        val result = cinemaService.getCinemaPage("popular", 1).blockingGet().results.size
        assertEquals(20, result)
    }
}