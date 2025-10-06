package com.example.cineflix.ui

// Android framework imports
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

// AndroidX and lifecycle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

// Project-specific imports
import com.example.cineflix.R
import com.example.cineflix.adapters.FollowingAdapter
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.viewmodel.ProfileViewModel

// Google Material Design components
import com.google.android.material.bottomnavigation.BottomNavigationView

// Kotlin coroutines for asynchronous flow collection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ProfileActivity displays user profile data including:
 * - Recently watched movies
 * - Highly rated movies
 * - Followed users' content
 *
 * It initializes RecyclerViews, observes data from ProfileViewModel,
 * and manages bottom navigation.
 */
class ProfileActivity : AppCompatActivity() {

    // ViewModel instance (lifecycle-aware)
    private val viewModel: ProfileViewModel by viewModels()

    // RecyclerView adapters
    private lateinit var recentlyWatchedAdapter: MovieAdapter
    private lateinit var highlyRatedAdapter: MovieAdapter
    private lateinit var followingAdapter: FollowingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Load profile data (TODO: replace hardcoded userId with actual auth user)
        viewModel.loadProfile("user123")

        initRecyclerViews()
        observeViewModel()
        setupBottomNavigation()
    }

    /**
     * Initializes RecyclerViews and attaches MovieAdapters.
     */
    private fun initRecyclerViews() {
        recentlyWatchedAdapter = MovieAdapter(emptyList())
        highlyRatedAdapter = MovieAdapter(emptyList())
        followingAdapter = FollowingAdapter(emptyList())

        findViewById<RecyclerView>(R.id.recyclerRecentlyWatched).adapter = recentlyWatchedAdapter
        findViewById<RecyclerView>(R.id.recyclerHighlyRated).adapter = highlyRatedAdapter
        findViewById<RecyclerView>(R.id.recyclerFollowing).adapter = followingAdapter
    }

    /**
     * Observes data flows from the ViewModel to update UI dynamically.
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.recentlyWatched.collectLatest { movies ->
                recentlyWatchedAdapter.updateMovies(movies)
            }
        }

        lifecycleScope.launch {
            viewModel.highlyRated.collectLatest { movies ->
                highlyRatedAdapter.updateMovies(movies)
            }
        }

        lifecycleScope.launch {
            viewModel.following.collectLatest { usernames ->
                followingAdapter.updateFollowing(usernames)
            }
        }
    }


    /**
     * Handles bottom navigation actions.
     */
    private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // TODO: Enable when MainActivity is ready
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_browse -> {
                    // TODO: Enable when BrowseMoviesActivity is ready
                    startActivity(Intent(this, BrowseMoviesActivity::class.java))
                    true
                }
                R.id.nav_mixes -> {
                    // TODO: Enable when BrowseMoviesActivity is ready
                    startActivity(Intent(this, BrowseMoviesActivity::class.java))
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}
