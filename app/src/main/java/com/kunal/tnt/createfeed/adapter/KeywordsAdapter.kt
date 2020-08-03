package com.kunal.tnt.createfeed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunal.tnt.R
import com.kunal.tnt.createfeed.data.Keywords
import com.kunal.tnt.databinding.ItemKeywordsBinding
import java.math.MathContext

class KeywordsAdapter(
    private var keywordsList: List<Keywords>
) :
    RecyclerView.Adapter<KeywordsAdapter.ViewHolder>() {

    lateinit var binding: ItemKeywordsBinding

    var listener: ((view: View, item: Keywords, position: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_keywords,
            parent,
            false
        )
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return keywordsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    fun addItems(items: List<Keywords>) {
        keywordsList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemKeywordsBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {

            binding.keyword = keywordsList[pos].name
            binding.root.setOnClickListener {
                listener?.invoke(binding.root, keywordsList[pos], pos)
            }

        }
    }


}