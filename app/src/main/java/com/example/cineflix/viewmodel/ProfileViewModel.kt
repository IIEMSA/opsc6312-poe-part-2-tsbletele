package com.example.cineflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineflix.model.Movie
import com.example.cineflix.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _recentlyWatched = MutableStateFlow<List<Movie>>(emptyList())
    val recentlyWatched: StateFlow<List<Movie>> = _recentlyWatched

    private val _highlyRated = MutableStateFlow<List<Movie>>(emptyList())
    val highlyRated: StateFlow<List<Movie>> = _highlyRated

    private val _following = MutableStateFlow<List<String>>(emptyList())
    val following: StateFlow<List<String>> = _following

    fun loadProfile(userId: String) {
        viewModelScope.launch {
            // TODO: Replace with real DB/API calls
            _recentlyWatched.value = repository.getRecentlyWatched(userId)
            _highlyRated.value = repository.getHighlyRated(userId)
            _following.value = repository.getFollowing(userId)
        }
    }
}
