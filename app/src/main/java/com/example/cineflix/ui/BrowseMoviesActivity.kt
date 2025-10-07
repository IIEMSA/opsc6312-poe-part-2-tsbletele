package com.example.cineflix.ui

import android.os.Bundle
import android.speech.RecognizerIntent
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cineflix.R
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.data.network.RetrofitClient
import com.example.cineflix.databinding.ActivityBrowseMoviesBinding
import com.example.cineflix.model.Movie
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import java.util.*

class BrowseMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrowseMoviesBinding
    private lateinit var adapter: MovieAdapter
    private val movies = mutableListOf<Movie>()

    private val api = RetrofitClient.api
    private val apiKey = "83258d157014d8e69ded25cc93f1e565"
    private val uiScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrowseMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSpinners()
        setupSearchBar()

        // Load default list
        loadMovies()
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter(movies)
        binding.recyclerBrowse.layoutManager = LinearLayoutManager(this)
        binding.recyclerBrowse.adapter = adapter
    }

    private fun setupSpinners() {
        // Genre Spinner
        val genres = resources.getStringArray(R.array.genres_array)
        val genreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genres)
        binding.spinnerGenre.adapter = genreAdapter

        // Sort Spinner
        val sortOptions = resources.getStringArray(R.array.sort_options)
        val sortAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortOptions)
        binding.spinnerSort.adapter = sortAdapter

        // Handle Genre Selection
        binding.spinnerGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                loadMovies() // reload when genre changes
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Handle Sort Selection
        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sortMovies()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSearchBar() {
        binding.searchButton.setOnClickListener {
            val query = binding.searchInput.text.toString().trim()
            if (query.isNotEmpty()) searchMovies(query)
        }

        binding.voiceButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a movie name...")
            try {
                startActivityForResult(intent, 100)
            } catch (e: Exception) {
                Log.e("VoiceSearch", "Speech recognition not supported", e)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0) ?: ""
            binding.searchInput.setText(spokenText)
            searchMovies(spokenText)
        }
    }
    // Map of TMDb genre IDs to readable names
    private val genreMap = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Sci-Fi",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )

    // Convert genre IDs → names
    private fun getGenreNames(genreIds: List<Int>?): List<String> {
        return genreIds?.mapNotNull { genreMap[it] } ?: emptyList()
    }


    private fun loadMovies() {
        val genre = binding.spinnerGenre.selectedItem.toString()
        showLoading(true)

        uiScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    api.getPopularMovies(apiKey)
                }

                movies.clear()
                movies.addAll(response.results.filter { movie ->
                    genre == "All Genres" || getGenreNames(movie.genreIds).contains(genre)
                })


                sortMovies()
                updateUIState()
            } catch (e: Exception) {
                Log.e("BrowseMovies", "Error loading movies", e)
                showError()
            }
        }
    }

    private fun searchMovies(query: String) {
        showLoading(true)
        uiScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    api.searchMovies(apiKey, query)
                }

                movies.clear()
                movies.addAll(response.results)
                sortMovies()
                updateUIState()
            } catch (e: Exception) {
                Log.e("SearchMovies", "Error searching", e)
                showError()
            }
        }
    }

    private fun sortMovies() {
        when (binding.spinnerSort.selectedItem.toString()) {
            "Top Rated" -> movies.sortByDescending { it.voteAverage }
            "Release Date" -> movies.sortByDescending { it.releaseDate }
            "Most Popular" -> movies.sortByDescending { it.popularity }
        }
        adapter.notifyDataSetChanged()
    }

    private fun updateUIState() {
        showLoading(false)
        binding.tvEmpty.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError() {
        showLoading(false)
        binding.tvEmpty.visibility = View.VISIBLE
        binding.tvEmpty.text = "Something went wrong. Try again."
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }

    private fun setupBottomNav(bottomNav: BottomNavigationView) {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_browse -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_mixes -> {

                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
