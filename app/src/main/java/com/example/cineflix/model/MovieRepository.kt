package com.example.cineflix.model

import com.example.cineflix.data.network.RetrofitClient

class MovieRepository {
    private val api = RetrofitClient.api

    // Upcoming (for MainActivity)
    suspend fun getUpcomingMovies(apiKey: String) =
        api.getUpcomingMovies(apiKey)

    // Discover with filters (for BrowseMoviesActivity)
    suspend fun discoverMovies(
        apiKey: String,
        genreId: String? = null,
        year: String? = null,
        minRating: Double? = null,
        page: Int = 1
    ) = api.discoverMovies(
        apiKey = apiKey,
        genreId = genreId,
        year = year,
        minRating = minRating,
        page = page
    )

    // Search by query
    suspend fun searchMovies(
        apiKey: String,
        query: String,
        page: Int = 1
    ) = api.searchMovies(
        apiKey = apiKey,
        query = query,
        page = page
    )
}
