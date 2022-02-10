package com.example.cinema_app.data.model

import android.graphics.Color
import com.example.cinema_app.data.entity.Cinema
import com.google.gson.annotations.SerializedName

data class CinemaModel(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

internal fun CinemaModel.toDomainModel() = Cinema(
    originalId = this.id,
    title = this.originalTitle,
    description = this.overview,
    image = "https://image.tmdb.org/t/p/w500/" + this.posterPath,
    titleColor = Color.BLACK,
    dateViewed = ""
)