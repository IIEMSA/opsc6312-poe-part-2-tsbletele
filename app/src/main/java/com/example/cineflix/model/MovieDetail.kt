package com.example.cineflix.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<Genre>,
    val poster_path: String?,
    val vote_average: Double
)

