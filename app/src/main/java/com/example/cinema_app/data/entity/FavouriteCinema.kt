package com.example.cinema_app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favouriteTable", indices = [Index(value = ["originalId"], unique = true)])
data class FavouriteCinema(
    val originalId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}