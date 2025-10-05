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

/**
 * ProfileViewModel handles the UI-related data for the Profile screen.
 * It retrieves user-specific data (e.g., recently watched, highly rated, and following)
 * from the UserRepository and exposes it as immutable StateFlows to the UI layer.
 */
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

    // Backing property for followed content (e.g., people, titles, etc.)
    private val _following = MutableStateFlow<List<String>>(emptyList())
    // Public immutable flow exposed to the UI
    val following: StateFlow<List<String>> = _following

    /**
     * Loads user profile data by calling the UserRepository methods.
     * This function should be triggered in the Activity or Fragment (usually in onCreate).
     *
     * @param userId The ID of the user whose profile data is being loaded.
     */
    fun loadProfile(userId: String) {
        viewModelScope.launch {
            // Fetch data from the repository (currently dummy data; replace with real API/DB)
            _recentlyWatched.value = repository.getRecentlyWatched(userId)
            _highlyRated.value = repository.getHighlyRated(userId)
            _following.value = repository.getFollowing(userId)
        }
    }
}
