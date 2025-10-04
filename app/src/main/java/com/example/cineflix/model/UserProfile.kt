package com.example.cineflix.model

data class UserProfile(
    val userId: String,
    val recentlyWatched: List<Movie>,
    val highlyRated: List<Movie>,
    val following: List<String>
)
