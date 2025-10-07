package com.example.cineflix.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Double,
    val genres: List<String>,
    val similarTitles: List<String>,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    val popularity: Double,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("genre_ids")
    val genreIds: List<Int>?
)
