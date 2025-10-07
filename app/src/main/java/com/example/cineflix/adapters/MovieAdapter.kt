package com.example.cineflix.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cineflix.R
import com.example.cineflix.model.Movie
import com.example.cineflix.ui.MovieDetailActivity

class MovieAdapter(
    private var movies: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.moviePoster)
        val title: TextView = itemView.findViewById(R.id.movieTitle)
        val stars: List<ImageView> = listOf(
            itemView.findViewById(R.id.star1),
            itemView.findViewById(R.id.star2),
            itemView.findViewById(R.id.star3),
            itemView.findViewById(R.id.star4),
            itemView.findViewById(R.id.star5)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_cards, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Bind movie data
        holder.title.text = movie.title

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .placeholder(R.drawable.yellowjackets)
            .into(holder.poster)

        // On click → open MovieDetailActivity
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailActivity::class.java).apply {
                putExtra("movieId", movie.id)
                putExtra("title", movie.title)
                putExtra("overview", movie.overview)
                putExtra("posterPath", movie.posterPath)
                putExtra("rating", movie.voteAverage)
                putStringArrayListExtra("genres", ArrayList(getGenreNames(movie.genreIds)))
            }
            context.startActivity(intent)
        }

        // Display stars based on rating
        val filledStars = (movie.rating / 2).toInt() // TMDB 0-10 → 0-5 scale
        for (i in holder.stars.indices) {
            if (i < filledStars) {
                holder.stars[i].setImageResource(R.drawable.star_filled)
            } else {
                holder.stars[i].setImageResource(R.drawable.star_outline)
            }
        }
    }

    override fun getItemCount() = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    // 🔹 Convert Genre IDs to Names
    private fun getGenreNames(ids: List<Int>?): List<String> {
        val map = mapOf(
            28 to "Action", 12 to "Adventure", 16 to "Animation", 35 to "Comedy",
            80 to "Crime", 99 to "Documentary", 18 to "Drama", 10751 to "Family",
            14 to "Fantasy", 36 to "History", 27 to "Horror", 10402 to "Music",
            9648 to "Mystery", 10749 to "Romance", 878 to "Sci-Fi",
            10770 to "TV Movie", 53 to "Thriller", 10752 to "War", 37 to "Western"
        )
        return ids?.mapNotNull { map[it] } ?: emptyList()
    }
}
