package com.example.cinema_app

import android.os.Parcelable
import androidx.annotation.ColorRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextViewColors(
        @ColorRes var firstTextColorId: Int,
        @ColorRes var secondTextColorId: Int,
        @ColorRes var thirdTextColorId: Int
) : Parcelable