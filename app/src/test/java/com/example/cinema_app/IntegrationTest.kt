package com.example.cinema_app

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cinema_app.data.CinemaService
import com.example.cinema_app.data.entity.Cinema
import com.example.cinema_app.data.model.CinemaListModel
import com.example.cinema_app.data.model.CinemaModel
import com.example.cinema_app.domain.CinemaListInteractor
import com.example.cinema_app.domain.ICinemaListInteractor
import com.example.cinema_app.presentation.view.cinemaList.CinemaListActivity
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel
import com.example.cinema_app.presentation.view.detail.CinemaActivity
import com.example.cinema_app.presentation.view.detail.CinemaDetailViewModel
import com.example.cinema_app.presentation.view.favourite.FavouriteViewModel
import com.example.cinema_app.service.FirebaseRemoteConfigService
import com.google.firebase.FirebaseApp
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IntegrationTest : RoomDB(){
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

    private val cinemaModel : ArrayList<CinemaModel> = ArrayList()

    //CinemaModel(true,"", listOf(1), 1, "","","",1.1, "", "", "", false, 1.1, 1)
    private var resultsPage = Single.just(CinemaListModel(1,1,1,
//        ArrayList(CinemaModel(true,"", listOf(1), 1, "","",
//            "",1.1, "", "", "", false, 1.1, 1)
//        )
    cinemaModel
    ))

    private lateinit var cinemaInteractor: ICinemaListInteractor
    //private lateinit var cinemaDAO: CinemaDaoTest
    private lateinit var cinemaService: CinemaService
    private lateinit var firebaseRemoteConfigService: FirebaseRemoteConfigService
    private lateinit var cinemaListViewModel: CinemaListViewModel


    private lateinit var cinemaDetailViewModel: CinemaDetailViewModel
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var cinemaListActivity: CinemaListActivity
    private lateinit var cinemaActivity: CinemaActivity
    lateinit var instrumentationContext: Context

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    @Before
    fun init() {
        cinemaModel.add(CinemaModel(true,"", listOf(1), 1, "","",
            "",1.1, "", "", "", false, 1.1, 1))
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        FirebaseApp.initializeApp(instrumentationContext)
        cinemaService = mock()
        firebaseRemoteConfigService = mock()
        cinemaInteractor = CinemaListInteractor(cinemaService)
        //cinemaDAO = mock()
        cinema.id = 1

        //Mockito.`when`(cinemaDao.getAll()).thenReturn(Flowable.just(listOf(cinema)))
        //Mockito.`when`(cinemaDao.searchCinema(title)).thenReturn(Flowable.just(listOf(cinema)))

       // Mockito.`when`(cinemaService.getCinemaPage("popular",1)).thenReturn(resultsPage)
        //cinemaService.getCinemaPage("popular",1).blockingGet()
//        Mockito.`when`(cinemaInteractor.cinemaItem).thenReturn(cinema)
        //Mockito.`when`(cinemaInteractor.getCinema(1)).thenReturn(results)
        cinemaListViewModel = CinemaListViewModel(cinemaInteractor, cinemaDao)
       // cinemaService.getCinemaPage("popular",1).blockingGet()
        //перенести
        cinemaListActivity = CinemaListActivity()
        cinemaActivity = CinemaActivity()
        cinemaDetailViewModel = CinemaDetailViewModel(cinemaInteractor, cinemaDao)
        cinemaActivity.viewModel = cinemaDetailViewModel
        favouriteViewModel = FavouriteViewModel(cinemaInteractor, cinemaDao)
    }

    @Test
    fun onSetCinemaItemCinemaListViewModelTest(){
        cinemaListViewModel.onSetCinemaItem(cinema)
        assertEquals(originalId, cinemaDetailViewModel.cinemaItem.originalId)
        assertEquals(originalId, cinemaActivity.viewModel.cinemaItem.originalId)
    }

    @Test
    fun onSetCinemaItemFavouriteViewModelTest(){
        favouriteViewModel.onSetCinemaItem(cinema)
        assertEquals(originalId, cinemaDetailViewModel.cinemaItem.originalId)
        assertEquals(originalId, cinemaActivity.viewModel.cinemaItem.originalId)
    }

    @Test
    fun onActivityResultCinemaListTest(){
        cinemaDao.insertCinema(cinema)
        cinemaDetailViewModel.onSetLikeClickListener(cinema, true)
        cinemaDetailViewModel.onGetLikedCinema()
        cinemaDetailViewModel.getLike(cinema)
        cinemaListViewModel.onGetHasLiked()
        assertTrue(cinemaListViewModel.hasLiked)
    }

}