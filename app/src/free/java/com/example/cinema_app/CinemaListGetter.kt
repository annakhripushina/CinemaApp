package com.example.cinema_app

import android.view.View
import com.example.cinema_app.presentation.view.cinemaList.CinemaListGetter
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel
import com.google.android.material.snackbar.Snackbar

class CinemaListGetter : CinemaListGetter {
    override fun onGetCinemaListPage(cinemaListViewModel: CinemaListViewModel, itemView: View) {
        val snackAddFavourite = Snackbar.make(
            itemView,
            "Продолжение доступно в платной версии:(",
            Snackbar.LENGTH_LONG
        )
        snackAddFavourite.show()
    }
}