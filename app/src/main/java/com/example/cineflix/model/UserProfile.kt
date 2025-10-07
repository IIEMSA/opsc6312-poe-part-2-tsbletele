package com.example.cineflix.model

data class UserProfile(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val following: List<String> = emptyList(),
    val favorites: List<Int> = emptyList(),
    val watchHistory: List<Int> = emptyList()
)
