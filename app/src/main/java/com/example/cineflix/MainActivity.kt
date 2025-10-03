package com.example.cineflix

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.data.network.RetrofitClient
import com.example.cineflix.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movieList = mutableListOf<Movie>()

    private val apiKey = "83258d157014d8e69ded25cc93f1e565"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieAdapter = MovieAdapter(movieList)
        recyclerView.adapter = movieAdapter

        fetchMovies()
    }

    private fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.getPopularMovies(apiKey)
                withContext(Dispatchers.Main) {
                    movieList.clear()
                    movieList.addAll(response.results)
                    movieAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching movies", e)
            }
        }
    }
}
