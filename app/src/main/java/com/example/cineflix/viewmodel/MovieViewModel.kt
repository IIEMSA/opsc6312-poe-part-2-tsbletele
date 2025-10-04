package com.example.cineflix.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineflix.model.Movie
import com.example.cineflix.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun fetchUpcomingMovies(apiKey: String) {
        viewModelScope.launch {
            val response = repo.getUpcomingMovies(apiKey)
            _movies.value = response.results
        }
    }
}