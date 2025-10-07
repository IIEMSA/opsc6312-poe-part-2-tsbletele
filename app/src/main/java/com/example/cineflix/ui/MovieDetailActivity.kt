package com.example.cineflix.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cineflix.R
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.data.network.RetrofitClient
import com.example.cineflix.model.Movie
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var posterImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieOverview: TextView
    private lateinit var genreChips: ChipGroup
    private lateinit var recyclerMoreLikeThis: RecyclerView
    private lateinit var backButton: ImageButton
    private lateinit var shareButton: ImageButton

    private val apiKey = "83258d157014d8e69ded25cc93f1e565"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Initialize views
        posterImage = findViewById(R.id.posterImage)
        movieTitle = findViewById(R.id.movieTitle)
        movieOverview = findViewById(R.id.movieOverview)
        genreChips = findViewById(R.id.genreChips)
        recyclerMoreLikeThis = findViewById(R.id.recyclerMoreLikeThis)
        backButton = findViewById(R.id.backButton)
        shareButton = findViewById(R.id.shareButton)

        // Get data from intent
        val movieId = intent.getIntExtra("movieId", -1)
        val title = intent.getStringExtra("title")
        val overview = intent.getStringExtra("overview")
        val posterPath = intent.getStringExtra("posterPath")
        val genres = intent.getStringArrayListExtra("genres") ?: arrayListOf()

        // Bind basic data
        movieTitle.text = title
        movieOverview.text = overview

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$posterPath")
            .placeholder(R.drawable.yellowjackets)
            .into(posterImage)

        // Display genre chips
        for (genre in genres) {
            val chip = Chip(this)
            chip.text = genre
            chip.isClickable = false
            chip.isCheckable = false
            genreChips.addView(chip)
        }

        // Handle back button
        backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Handle share button
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Check out this movie!")
                putExtra(Intent.EXTRA_TEXT, "$title\n\n$overview")
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        // Load similar movies
        if (movieId != -1) {
            fetchSimilarMovies(movieId)
        } else {
            Toast.makeText(this, "Movie not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchSimilarMovies(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.getSimilarMovies(movieId, apiKey)
                withContext(Dispatchers.Main) {
                    recyclerMoreLikeThis.layoutManager =
                        LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                    recyclerMoreLikeThis.adapter = MovieAdapter(response.results)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MovieDetailActivity, "Failed to load similar movies", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
