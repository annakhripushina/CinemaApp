package com.example.cinema_app.data.model

import com.google.gson.annotations.SerializedName

data class CinemaListModel(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResult: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: ArrayList<CinemaModel>
)