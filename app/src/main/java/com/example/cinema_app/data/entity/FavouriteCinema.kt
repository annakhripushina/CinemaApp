package com.example.cinema_app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table", indices = [Index(value = ["original_id"], unique = true)])
data class FavouriteCinema(
    val original_id: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}