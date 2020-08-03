package com.kunal.tnt.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities.formatDate
import com.kunal.tnt.databinding.ItemDailyFeedBinding
import com.kunal.tnt.home.data.Feed

class FeedsAdapter(
    private var feedsList: List<Feed>
) :
    RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    lateinit var binding: ItemDailyFeedBinding

    var listener: ((view: View, item: Feed, position: Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_daily_feed,
            parent,
            false
        )
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return feedsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    fun addItems(items: List<Feed>) {
        feedsList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemDailyFeedBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {

            binding.feed = feedsList[pos]
            binding.txtDate.text = formatDate(feedsList[pos].createdAt)
            binding.root.setOnClickListener {
                listener?.invoke(binding.root, feedsList[pos], pos)
            }

        }
    }


}