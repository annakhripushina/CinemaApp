package com.example.cinema_app.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinema_app.CinemaItem
import com.example.cinema_app.dao.RoomDB
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.model.CinemaModel
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.domain.ICinemaListInteractor
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.presentation.view.detail.CinemaDetailViewModel
import com.example.cinema_app.presentation.view.favourite.FavouriteViewModel
import com.example.cinema_app.service.FirebaseRemoteConfigService
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ViewModelsSwapTest : RoomDB() {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val cinema = CinemaItem.cinema
    private val cinemaModel: ArrayList<CinemaModel> = ArrayList()

    private lateinit var cinemaInteractor: ICinemaListInteractor
    private lateinit var cinemaService: CinemaService
    private lateinit var firebaseRemoteConfigService: FirebaseRemoteConfigService
    private lateinit var cinemaListViewModel: CinemaListViewModel
    private lateinit var cinemaDetailViewModel: CinemaDetailViewModel
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var cinemaActivity: CinemaActivity
    private lateinit var instrumentationContext: Context

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    @Before
    fun init() {
        cinema.id = 1
        cinemaModel.add(
            CinemaModel(
                true, "", listOf(1), 1, "", "",
                "", 1.1, "", "", "", false, 1.1, 1
            )
        )

        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        cinemaService = mock()
        firebaseRemoteConfigService = mock()
        cinemaInteractor = CinemaListInteractor(cinemaService)

        cinemaListViewModel = CinemaListViewModel(cinemaInteractor, cinemaDao)
        cinemaDetailViewModel = CinemaDetailViewModel(cinemaInteractor, cinemaDao)
        favouriteViewModel = FavouriteViewModel(cinemaInteractor, cinemaDao)

        cinemaActivity = CinemaActivity()
        cinemaActivity.viewModel = cinemaDetailViewModel

    }

    //Интеграционные тесты
    @Test
    fun onSetCinemaItemCinemaListViewModelTest() {
        cinemaListViewModel.onSetCinemaItem(cinema)
        assertEquals(CinemaItem.originalId, cinemaDetailViewModel.cinemaItem.originalId)
        assertEquals(CinemaItem.originalId, cinemaActivity.viewModel.cinemaItem.originalId)
    }

    @Test
    fun onSetCinemaItemFavouriteViewModelTest() {
        favouriteViewModel.onSetCinemaItem(cinema)
        assertEquals(CinemaItem.originalId, cinemaDetailViewModel.cinemaItem.originalId)
        assertEquals(CinemaItem.originalId, cinemaActivity.viewModel.cinemaItem.originalId)
    }

    @Test
    fun onActivityResultCinemaListTest() {
        cinemaDao.insertCinema(cinema)
        cinemaDetailViewModel.onSetLikeClickListener(cinema, true)
        cinemaDetailViewModel.onGetLikedCinema()
        cinemaDetailViewModel.getLike(cinema)
        cinemaListViewModel.onGetHasLiked()
        assertTrue(cinemaListViewModel.hasLiked)
    }

}