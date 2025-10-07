package com.example.cineflix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cineflix.R
import com.example.cineflix.model.Movie

/**
 * Adapter for displaying 'More Like This' movies in MovieDetailActivity.
 * It binds movie posters and titles to RecyclerView items.
 */
class MovieDetailAdapter(
    private var movies: List<Movie> = listOf(),
    private val onMovieClick: ((Movie) -> Unit)? = null
) : RecyclerView.Adapter<MovieDetailAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.moviePoster)
        val title: TextView = view.findViewById(R.id.movieTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_small, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .placeholder(R.drawable.spiderverse_poster)
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            onMovieClick?.invoke(movie)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }


}
