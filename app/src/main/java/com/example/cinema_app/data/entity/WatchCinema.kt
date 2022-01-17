package com.example.cinema_app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "watch_table", indices = [Index(value = ["original_id"], unique = true)])
data class WatchCinema(
    val original_id: Int,
    val date_viewed: String,
    val request_code: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}