package com.example.cineflix.ui

// Android framework imports
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

// AndroidX and lifecycle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

// Project-specific imports
import com.example.cineflix.R
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.viewmodel.ProfileViewModel

// Google Material Design components
import com.google.android.material.bottomnavigation.BottomNavigationView

// Kotlin coroutines for asynchronous flow collection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ProfileActivity is the screen displaying user profile data including recently watched movies, 
 * highly rated movies, and followed content.
 * It sets up RecyclerViews with adapters, observes data from ProfileViewModel,
 * and manages the bottom navigation bar.
 */
class ProfileActivity : AppCompatActivity() {

    // Get ViewModel scoped to this activity
    private val viewModel: ProfileViewModel by viewModels()

    // Adapters for different movie categories
    private lateinit var recentlyWatchedAdapter: MovieAdapter
    private lateinit var highlyRatedAdapter: MovieAdapter
    private lateinit var followingAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Load user profile data (replace with actual user ID when integrated)
        viewModel.loadProfile("user123")

        initRecyclerViews()
        observeViewModel()
        setupBottomNavigation()
    }

    /**
     * Initializes the RecyclerViews and binds them to their corresponding adapters.
     */
    private fun initRecyclerViews() {
        recentlyWatchedAdapter = MovieAdapter()
        highlyRatedAdapter = MovieAdapter()
        followingAdapter = MovieAdapter()

        findViewById<RecyclerView>(R.id.recyclerRecentlyWatched).adapter = recentlyWatchedAdapter
        findViewById<RecyclerView>(R.id.recyclerHighlyRated).adapter = highlyRatedAdapter
        findViewById<RecyclerView>(R.id.recyclerFollowing).adapter = followingAdapter
    }

    /**
     * Observes StateFlows from the ViewModel and updates adapters with the latest data.
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.recentlyWatched.collectLatest { movies ->
                recentlyWatchedAdapter.submitList(movies)
            }
        }

        lifecycleScope.launch {
            viewModel.highlyRated.collectLatest { movies ->
                highlyRatedAdapter.submitList(movies)
            }
        }

        lifecycleScope.launch {
            viewModel.following.collectLatest { movies ->
                followingAdapter.submitList(movies)
            }
        }
    }

    /**
     * Sets up the bottom navigation and handles navigation item selection.
     */
    private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.menu_browse -> {
                    // startActivity(Intent(this, BrowseMoviesActivity::class.java))
                    true
                }
                R.id.menu_profile -> {
                    true // Already on this page
                }
                else -> false
            }
        }
    }
}
