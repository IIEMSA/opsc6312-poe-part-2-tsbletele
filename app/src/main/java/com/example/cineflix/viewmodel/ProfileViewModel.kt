package com.example.cineflix.viewmodel

// Android architecture components
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

// Project-specific model and repository
import com.example.cineflix.model.Movie
import com.example.cineflix.repository.UserRepository

// Kotlin Coroutines
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    // Backing property for recently watched movies
    private val _recentlyWatched = MutableStateFlow<List<Movie>>(emptyList())
    // Public immutable flow exposed to the UI
    val recentlyWatched: StateFlow<List<Movie>> = _recentlyWatched

    // Backing property for highly rated movies
    private val _highlyRated = MutableStateFlow<List<Movie>>(emptyList())
    // Public immutable flow exposed to the UI
    val highlyRated: StateFlow<List<Movie>> = _highlyRated
