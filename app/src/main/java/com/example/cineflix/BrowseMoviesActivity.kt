package com.example.cineflix

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.adapters.MovieAdapter
import com.example.cineflix.data.network.RetrofitClient
import com.example.cineflix.model.MovieRepository
import com.example.cineflix.viewmodel.BrowseMoviesViewModel

class BrowseMoviesActivity : AppCompatActivity() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: BrowseMoviesViewModel

    private val apiKey = "83258d157014d8e69ded25cc93f1e565"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_movies)

        // UI
        val searchView = findViewById<SearchView>(R.id.searchView)
        val genreSpinner = findViewById<Spinner>(R.id.genreSpinner)
        val yearSpinner = findViewById<Spinner>(R.id.yearSpinner)
        val ratingSpinner = findViewById<Spinner>(R.id.ratingSpinner)
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView)
        progressBar = findViewById(R.id.paginationLoader)

        // Setup RecyclerView
        adapter = MovieAdapter(emptyList())
        moviesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.adapter = adapter

        // Setup Repository + ViewModel
        val api = RetrofitClient.api
        val repo = MovieRepository()
        viewModel = BrowseMoviesViewModel(repo)

        // Observe data
        lifecycleScope.launchWhenStarted {
            viewModel.movies.collect { list ->
                adapter = MovieAdapter(list)
                moviesRecyclerView.adapter = adapter
                progressBar.visibility = View.GONE
            }
        }

        // Filters
        genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                reloadMovies()
            }
            override fun onNothingSelected(p0: AdapterView<*>) {}
        }
        yearSpinner.onItemSelectedListener = genreSpinner.onItemSelectedListener
        ratingSpinner.onItemSelectedListener = genreSpinner.onItemSelectedListener

        // Search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                reloadMovies(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        // Pagination
        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    progressBar.visibility = View.VISIBLE
                    reloadMovies()
                }
            }
        })

        // Initial load
        reloadMovies()
    }

    private fun reloadMovies(query: String? = null) {
        progressBar.visibility = View.VISIBLE
        val genreId = findViewById<Spinner>(R.id.genreSpinner).selectedItem.toString()
        val year = findViewById<Spinner>(R.id.yearSpinner).selectedItem.toString()
        val rating = findViewById<Spinner>(R.id.ratingSpinner).selectedItem.toString()

        viewModel.loadMovies(
            apiKey = apiKey,
            genreId = if (genreId != "All") genreId else null,
            year = if (year != "All") year else null,
            minRating = if (rating != "All") rating.replace("+", "").toDoubleOrNull() else null
        )
    }
}
