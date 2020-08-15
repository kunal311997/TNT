package com.kunal.tnt.favourites.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kunal.tnt.R
import com.kunal.tnt.favourites.models.Favourites
import javax.inject.Inject

class FavouritesAdapter @Inject constructor(
    private var favourites: List<Favourites>
) : RecyclerView.Adapter<FavouritesAdapter.WordViewHolder>() {

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = favourites[position]
        holder.wordItemView.text = current.title
    }

    internal fun setWords(words: List<Favourites>) {
        this.favourites = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = favourites.size
}