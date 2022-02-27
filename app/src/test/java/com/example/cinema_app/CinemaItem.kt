package com.example.cinema_app

import com.example.cinema_app.data.entity.Cinema

object CinemaItem {
    val originalId = 524434
    val title = "Eternals"
    val description =
        "The Eternals are a team of ancient aliens who have been living on Earth in secret for thousands of years."
    val image = "https://image.tmdb.org/t/p/w500//bcCBq9N1EMo3daNIjWJ8kYvrQm6.jpg"
    var titleColor = -16777216
    val dateViewed = ""
    val cinema = Cinema(originalId, title, description, image, titleColor, dateViewed)
}