package com.example.cineflix.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.R
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.viewmodel.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel by viewModels ()

    private lateinit var recentlyWatchedAdapter: MovieAdapter
    private lateinit var highlyRatedAdapter: MovieAdapter
    private lateinit var followingAdapter: MovieAdapter

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
