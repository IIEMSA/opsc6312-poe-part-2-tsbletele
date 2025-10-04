package com.example.cineflix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineflix.model.CineWrapped
import com.example.cineflix.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CineWrappedViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val _cineWrapped = MutableStateFlow<CineWrapped?>(null)
    val cineWrapped: StateFlow<CineWrapped?> = _cineWrapped

    fun loadCineWrapped(userId: String) {
        viewModelScope.launch {
            // TODO: Fetch data from repository
            // _cineWrapped.value = repository.getCineWrapped(userId)
        }
    }
}
