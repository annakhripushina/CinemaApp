package com.example.cinema_app.presentation.view.cinemaList

import android.view.View

interface CinemaListGetter {
    fun onGetCinemaListPage(viewModel: CinemaListViewModel, itemView: View)
}