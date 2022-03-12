package com.example.cinema_app.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cinema_app.CinemaItem
import com.example.cinema_app.data.ICinemaRepository
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.domain.ICinemaListInteractor
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CinemaListViewModelTest {
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
    private lateinit var cinemaRepository: ICinemaRepository
    private lateinit var viewModel: CinemaListViewModel

    private inline fun <reified T> mock(): T = mock(T::class.java)

    @Before
    fun init() {
        cinemaInteractor = mock()
        cinemaRepository = mock()
        cinema.id = 1

        `when`(cinemaRepository.getAllCinema()).thenReturn(Flowable.just(listOf(cinema)))
        `when`(cinemaRepository.searchCinema(CinemaItem.title)).thenReturn(
            Flowable.just(
                listOf(
                    cinema
                )
            )
        )
        `when`(cinemaInteractor.getCinema(anyInt())).thenReturn(results)
        `when`(cinemaInteractor.cinemaItem).thenReturn(cinema)

        viewModel = CinemaListViewModel(cinemaInteractor, cinemaRepository)

    }

    @Test
    fun onGetCinemaTest() {
        assertNotNull(viewModel.onGetAllCinema())
        viewModel.allCinema.value?.let {
            assertAll("Cinema",
                { assertEquals(CinemaItem.originalId, it[0].originalId) },
                { assertEquals(CinemaItem.title, it[0].title) },
                { assertEquals(CinemaItem.description, it[0].description) },
                { assertEquals(CinemaItem.image, it[0].image) },
                { assertEquals(CinemaItem.titleColor, it[0].titleColor) },
                { assertEquals(CinemaItem.dateViewed, it[0].dateViewed) }
            )
        }
    }

    @Test
    fun onGetCinemaListTest() {
        viewModel.onGetCinemaList(anyInt())
        verify(cinemaInteractor, atLeastOnce()).getCinema(anyInt())
    }

    @Test
    fun onSetCinemaItem() {
        viewModel.onSetCinemaItem(cinema)
        verify(cinemaInteractor, atLeastOnce()).onSetCinemaItem(cinema)
    }

    @Test
    fun onSearchCinemaTest() {
        val searchCinema = viewModel.onSearchCinema(cinema.title).value
        assertEquals(CinemaItem.originalId, searchCinema?.get(0)?.originalId)
    }

}