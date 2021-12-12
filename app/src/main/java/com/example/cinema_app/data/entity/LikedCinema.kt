package com.example.cinema_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_table")
data class LikedCinema(
    @PrimaryKey
    val original_id: Int
)