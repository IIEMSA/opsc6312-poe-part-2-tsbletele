package com.example.cineflix.model

class UserRepository {
    // TODO: Connect with Room or Firebase to store/retrieve user watch data
    suspend fun getCineWrapped(userId: String): CineWrapped {
        // TODO: Replace with real data source
        return CineWrapped(
            persona = "🎨 The Dreamwalker",
            hoursWatched = 120
        )
    }
}
