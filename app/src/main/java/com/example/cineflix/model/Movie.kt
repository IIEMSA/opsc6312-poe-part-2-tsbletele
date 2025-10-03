package com.example.cineflix.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?,
    val rating: Double
)
