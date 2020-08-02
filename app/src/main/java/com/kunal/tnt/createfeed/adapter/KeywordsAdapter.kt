package com.kunal.tnt.createfeed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunal.tnt.R
import com.kunal.tnt.databinding.ItemKeywordsBinding

class KeywordsAdapter(
    private val keywordsList: ArrayList<String>
) :
    RecyclerView.Adapter<KeywordsAdapter.ViewHolder>() {

    lateinit var binding: ItemKeywordsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_keywords,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return keywordsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class ViewHolder(val binding: ItemKeywordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(pos: Int) {

            binding.keyword = keywordsList[pos]

        }
    }


}