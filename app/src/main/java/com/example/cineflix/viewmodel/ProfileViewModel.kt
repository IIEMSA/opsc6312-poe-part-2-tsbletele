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


