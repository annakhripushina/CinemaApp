package com.example.cinema_app

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cinema(
        val id: Int,
        @StringRes val title: Int,
        @StringRes val description: Int?,
        @DrawableRes val image: Int?,
        var like: Boolean
) : Parcelable