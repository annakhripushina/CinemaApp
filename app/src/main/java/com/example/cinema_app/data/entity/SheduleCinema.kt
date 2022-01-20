package com.example.cinema_app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "shedule_table", indices = [Index(value = ["original_id"], unique = true)])
data class SheduleCinema(
    val original_id: Int,
    val date_viewed: String,
    val request_code: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}