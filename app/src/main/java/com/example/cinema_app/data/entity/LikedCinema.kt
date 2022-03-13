package com.example.cinema_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "likedTable")
data class LikedCinema(
    @PrimaryKey
    val originalId: Int
)