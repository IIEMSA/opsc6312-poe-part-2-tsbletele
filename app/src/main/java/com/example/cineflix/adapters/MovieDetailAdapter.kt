package com.example.cineflix

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.R

class MovieDetailAdapter(
    private var items: List<String> = listOf()
) : RecyclerView.Adapter<MovieDetailAdapter.ViewHolder>() {

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.simple_text_item, parent, false)
        ) {
        val text: TextView = itemView.findViewById(R.id.simpleText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<String>) {
        items = newList
        notifyDataSetChanged()
    }
}
