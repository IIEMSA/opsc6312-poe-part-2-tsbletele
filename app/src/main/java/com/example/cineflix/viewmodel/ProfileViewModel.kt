package com.example.cineflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineflix.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

 private val _recentlyWatched = MutableStateFlow<List<Movie>>(emptyList())
 val recentlyWatched: StateFlow<List<Movie>> = _recentlyWatched

 private val _highlyRated = MutableStateFlow<List<Movie>>(emptyList())
 val highlyRated: StateFlow<List<Movie>> = _highlyRated

 private val _following = MutableStateFlow<List<Movie>>(emptyList())
 val following: StateFlow<List<Movie>> = _following

 init {
 fetchDummyProfileData()
 }

 private fun fetchDummyProfileData() {
 viewModelScope.launch {
 // TODO: Replace with real data or API calls if needed
 _recentlyWatched.value = listOf(
 Movie(id = 1, title = "The Matrix", posterPath = "", rating = 4.5),
 Movie(id = 2, title = "Inception", posterPath = "", rating = 4.7)
 )

 _highlyRated.value = listOf(
 Movie(id = 3, title = "Interstellar", posterPath = "", rating = 4.8),
 Movie(id = 4, title = "Parasite", posterPath = "", rating = 4.9)
 )

 _following.value = listOf(
 Movie(id = 5, title = "The Dark Knight", posterPath = "", rating = 4.9),
 Movie(id = 6, title = "Fight Club", posterPath = "", rating = 4.6)
 )
 }
 }
}
