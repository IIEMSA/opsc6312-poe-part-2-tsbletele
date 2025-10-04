package com.example.cineflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineflix.repository.MovieRepository
import com.example.cineflix.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BrowseMoviesViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private var currentPage = 1

    fun loadMovies(apiKey: String, genreId: String?, year: String?, minRating: Double?) {
        viewModelScope.launch {
            val response = repository.discoverMovies(apiKey, genreId, year, minRating, currentPage)
            _movies.value = _movies.value + response.results
            currentPage++
        }
    }
}
