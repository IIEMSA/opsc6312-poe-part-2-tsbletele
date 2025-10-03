package com.example.cineflix

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineflix.model.Movie
import com.example.cineflix.model.MovieDetail
import com.example.cineflix.model.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail

    private val _similarMovies = MutableStateFlow<List<Movie>>(emptyList())
    val similarMovies: StateFlow<List<Movie>> = _similarMovies

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            // TODO: Call repository.getMovieDetails(movieId)
            // TODO: Call repository.getSimilarMovies(movieId)
        }
    }
}
