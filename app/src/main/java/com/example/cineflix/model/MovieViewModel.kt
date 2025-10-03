package com.example.cineflix.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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