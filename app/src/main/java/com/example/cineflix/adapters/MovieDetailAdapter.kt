package com.example.cineflix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.R
import com.example.cineflix.model.Movie

class MovieDetailAdapter(
    private var movies: List<Movie>
) : RecyclerView.Adapter<MovieDetailAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO: Bind poster, title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_cards, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        // TODO: Bind data (use Glide for poster)
    }

    override fun getItemCount(): Int = movies.size
}
