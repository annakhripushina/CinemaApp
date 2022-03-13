package com.example.cinema_app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "scheduleTable", indices = [Index(value = ["originalId"], unique = true)])
data class ScheduleCinema(
    val originalId: Int,
    val dateAlarm: String,
    val dateViewed: String,
    val requestCode: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}