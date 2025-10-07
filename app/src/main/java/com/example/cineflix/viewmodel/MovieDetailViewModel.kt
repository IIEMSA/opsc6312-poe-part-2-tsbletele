package com.example.cineflix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineflix.model.Movie
import com.example.cineflix.data.network.RetrofitClient
import kotlinx.coroutines.launch
import android.util.Log
import com.example.cineflix.model.MovieDetail

/**
 * ViewModel for MovieDetailActivity.
 * Fetches and exposes movie details using the TMDB API.
 */
class MovieDetailViewModel : ViewModel() {

    private val _movie = MutableLiveData<MovieDetail>()
    val movie: LiveData<MovieDetail> get() = _movie

    private val api = RetrofitClient.api
    private val apiKey = "83258d157014d8e69ded25cc93f1e565"

    /**
     * Fetches movie details from TMDB using the movie ID.
     */
    fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = api.getMovieDetails(id, apiKey)
                _movie.value = response
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error fetching movie details", e)
            }
        }
    }

    private val _similarMovies = MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> get() = _similarMovies

    fun loadSimilarMovies(id: Int) {
        viewModelScope.launch {
            try {
                val response = api.getSimilarMovies(id, apiKey)
                _similarMovies.value = response.results
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error fetching similar movies", e)
            }
        }
    }

}
