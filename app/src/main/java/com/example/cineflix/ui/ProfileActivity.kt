package com.example.cineflix.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.R
import com.example.cineflix.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel

    private lateinit var recyclerRecentlyWatched: RecyclerView
    private lateinit var recyclerHighlyRated: RecyclerView
    private lateinit var recyclerFollowing: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        recyclerRecentlyWatched = findViewById(R.id.recyclerRecentlyWatched)
        recyclerHighlyRated = findViewById(R.id.recyclerHighlyRated)
        recyclerFollowing = findViewById(R.id.recyclerFollowing)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        // TODO: Setup adapters (e.g., MovieAdapter for movies, a different adapter for following if needed)

        // TODO: Observe ViewModel data and bind to RecyclerViews
    }
}
