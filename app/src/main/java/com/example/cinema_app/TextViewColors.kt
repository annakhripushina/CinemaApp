package com.example.cinema_app
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextViewColors(var firstTextColor: Int, var secondTextColor: Int, var thirdTextColor: Int) : Parcelable {
}