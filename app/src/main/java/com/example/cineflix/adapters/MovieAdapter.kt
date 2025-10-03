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

class MovieAdapter(
    private val movies: List<Movie>
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

        // Load poster from TMDB
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .placeholder(R.drawable.yellowjackets) // fallback image
            .into(holder.poster)

        holder.title.text = movie.title

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
}
