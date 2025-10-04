package com.example.cineflix.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.MovieDetailAdapter
import com.example.cineflix.MovieDetailViewModel
import com.example.cineflix.R

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var moreLikeThisRecycler: RecyclerView
    private lateinit var adapter: MovieDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // TODO: Bind views (poster, title, overview, stars, back, share, etc.)
        moreLikeThisRecycler = findViewById(R.id.recyclerMoreLikeThis)

        adapter = MovieDetailAdapter(emptyList())
        moreLikeThisRecycler.adapter = adapter

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        // TODO: Get movieId from Intent and call viewModel.loadMovieDetails(movieId)
    }
}
