package com.example.cineflix

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<String>,
    val similarTitles: List<String>
)

class MovieDetailViewModel : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> get() = _movie

    fun loadMovieDetails(id: Int) {
        // Mock data for demonstration — you’ll replace this with a real data source later
        val demoMovie = Movie(
            id = id,
            title = "Spider-Man: Across the Spider-Verse",
            overview = "After reuniting with Gwen Stacy, Miles Morales embarks on an adventure...",
            genres = listOf("Animation", "Action", "Adventure"),
            similarTitles = listOf(
                "Into the Spider-Verse",
                "No Way Home",
                "Homecoming",
                "Far From Home"
            )
        )
        _movie.value = demoMovie
    }
}
