package com.example.cinema_app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cinema (val id: Int, val title: Int, val description: Int?, val image: Int?, var like: Boolean) : Parcelable {

}