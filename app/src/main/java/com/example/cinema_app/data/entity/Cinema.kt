package com.example.cinema_app.data.entity

import androidx.annotation.ColorInt
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cinema_table", indices = [Index(value = ["original_id"], unique = true)])
data class Cinema(
    val original_id: Int,
    val title: String,
    val description: String,
    val image: String,
    @ColorInt var titleColor: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}