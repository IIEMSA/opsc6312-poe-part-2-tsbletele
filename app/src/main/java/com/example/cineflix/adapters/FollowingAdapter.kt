package com.example.cineflix.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cineflix.R

/**
 * Adapter for displaying a list of followed users (usernames).
 */
class FollowingAdapter(
    private var followingList: List<String>
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameText: TextView = itemView.findViewById(R.id.followingItemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_following, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.usernameText.text = followingList[position]
    }

    override fun getItemCount() = followingList.size

    // ✅ Update list dynamically
    fun updateFollowing(newList: List<String>) {
        followingList = newList
        notifyDataSetChanged()
    }
}
