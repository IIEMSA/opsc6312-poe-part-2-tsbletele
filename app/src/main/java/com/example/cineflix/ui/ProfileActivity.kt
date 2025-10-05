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

    // Using Kotlin property delegate to get ViewModel instance scoped to this Activity
    private val viewModel: ProfileViewModel by viewModels()

    // Adapters to display lists of movies in RecyclerViews for each section
    private lateinit var recentlyWatchedAdapter: MovieAdapter
    private lateinit var highlyRatedAdapter: MovieAdapter
    private lateinit var followingAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout XML for the profile screen
        setContentView(R.layout.activity_profile)

     // Load user-specific profile data (use real user ID when available)
        viewModel.loadProfile("user123")
        
        // Initialize RecyclerViews and set their adapters
        initRecyclerViews()
        
        // Observe data streams from ViewModel and update UI accordingly
        observeViewModel()
        
        // Configure bottom navigation item selection behavior
        setupBottomNavigation()
    }

    /**
     * Initializes the RecyclerView adapters and binds them to their respective RecyclerViews
     * in the layout. This prepares the lists for displaying movie data.
     */
    private fun initRecyclerViews() {
        // Instantiate new adapters for each movie list
        recentlyWatchedAdapter = MovieAdapter()
        highlyRatedAdapter = MovieAdapter()
        followingAdapter = MovieAdapter()

        // Bind adapters to RecyclerViews by finding views with their IDs
        findViewById<RecyclerView>(R.id.recyclerRecentlyWatched).adapter = recentlyWatchedAdapter
        findViewById<RecyclerView>(R.id.recyclerHighlyRated).adapter = highlyRatedAdapter
        findViewById<RecyclerView>(R.id.recyclerFollowing).adapter = followingAdapter
    }

  private fun observeViewModel() {
    // Launch a coroutine tied to the Activity's lifecycle
    lifecycleScope.launch {
        // Collect the latest list of recently watched movies from the ViewModel
        viewModel.recentlyWatched.collectLatest { movies ->
            // Submit the updated list to the RecyclerView adapter to refresh the UI
            recentlyWatchedAdapter.submitList(movies)
        }
    }

    lifecycleScope.launch {
        // Collect the latest list of highly rated movies from the ViewModel
        viewModel.highlyRated.collectLatest { movies ->
            // Update the highly rated movies adapter with new data
            highlyRatedAdapter.submitList(movies)
        }
    }

    lifecycleScope.launch {
        // Collect the latest list of followed movies or content from the ViewModel
        viewModel.following.collectLatest { movies ->
            // Submit the new list to the following adapter to update the UI
            followingAdapter.submitList(movies)
        }
    }
}

private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Navigate to HomeActivity (uncomment when needed)
                    // startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.menu_browse -> {
                    // Navigate to BrowseMoviesActivity (uncomment when needed)
                    // startActivity(Intent(this, BrowseMoviesActivity::class.java))
                    true
                }
                R.id.menu_profile -> {
                    true // Already here
                }
                else -> false
            }
        }
    }
}
