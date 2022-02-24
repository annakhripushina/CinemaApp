package com.example.cinema_app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.room.CinemaDao
import com.example.cinema_app.domain.ICinemaListInteractor
import com.example.cinema_app.presentation.view.cinemaList.CinemaListActivity
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.presentation.view.detail.CinemaDetailViewModel
import com.example.cinema_app.presentation.view.favourite.FavouriteViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.stubbing.Answer
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CinemaListViewModelTest{
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
    private var results = Single.just(listOf(cinema))
    private lateinit var cinemaInteractor: ICinemaListInteractor
    private lateinit var cinemaDAO: CinemaDao
    private lateinit var viewModel: CinemaListViewModel

    inline fun <reified T> mock(): T = mock(T::class.java)

    @Before
    fun init() {
        cinemaInteractor = mock()
        cinemaDAO = mock()
        cinema.id = 1

        `when`(cinemaDAO.getAll()).thenReturn(Flowable.just(listOf(cinema)))
        `when`(cinemaDAO.searchCinema(title)).thenReturn(Flowable.just(listOf(cinema)))
        `when`(cinemaInteractor.getCinema(anyInt())).thenReturn(results)
        `when`(cinemaInteractor.cinemaItem).thenReturn(cinema)

        viewModel = CinemaListViewModel(cinemaInteractor, cinemaDAO)

    }

    @Test
    fun onGetCinemaTest() {
        assertNotNull(viewModel.onGetAllCinema())
        assertAll("Cinema",
            { assertEquals(originalId, viewModel.allCinema.value!![0].originalId) },
            { assertEquals(title, viewModel.allCinema.value!![0].title) },
            { assertEquals(description, viewModel.allCinema.value!![0].description) },
            { assertEquals(image, viewModel.allCinema.value!![0].image) },
            { assertEquals(titleColor, viewModel.allCinema.value!![0].titleColor) },
            { assertEquals(dateViewed, viewModel.allCinema.value!![0].dateViewed) }
        )

    }

    @Test
    fun onGetCinemaListTest() {
        viewModel.onGetCinemaList(anyInt())
        verify(cinemaInteractor, atLeast(2)).getCinema(anyInt())
    }

    @Test
    fun onSetCinemaItem() {
        viewModel.onSetCinemaItem(cinema)
        verify(cinemaInteractor, atLeastOnce()).onSetCinemaItem(cinema)
    }

    @Test
    fun onSearchCinemaTest(){
        val searchCinema = viewModel.onSearchCinema(cinema.title).value
        assertEquals(originalId, searchCinema?.get(0)?.originalId)
    }

    @Test
    fun updateTitleColorTest(){
        viewModel.updateTitleColor(0, cinema.id!!)
        verify(cinemaDAO).updateTitleColor(0, cinema.id!!)
    }

}