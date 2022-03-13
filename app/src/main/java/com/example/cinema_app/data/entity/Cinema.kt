package com.example.cinema_app.data.entity

import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cinemaTable", indices = [Index(value = ["originalId"], unique = true)])
data class Cinema(
    val originalId: Int,
    val title: String,
    val description: String,
    val image: String,
    @ColorInt var titleColor: Int,
    val dateViewed: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}