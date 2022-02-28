package com.example.cinema_app

import android.view.View
import com.example.cinema_app.presentation.view.cinemaList.CinemaListGetter
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel

class CinemaListGetter : CinemaListGetter {
    override fun onGetCinemaListPage(cinemaListViewModel: CinemaListViewModel, itemView: View) {
        cinemaListViewModel.onGetCinemaListPage()
    }

}