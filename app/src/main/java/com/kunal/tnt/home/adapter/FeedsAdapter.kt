package com.kunal.tnt.home.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities.formatDate
import com.kunal.tnt.databinding.ItemDailyFeedBinding
import com.kunal.tnt.databinding.ItemDailyFeedTextBinding
import com.kunal.tnt.home.data.Feed

class FeedsAdapter(
    private var feedsList: List<Feed>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemDailyFeedBinding: ItemDailyFeedBinding
    lateinit var itemDailyFeedTextBinding: ItemDailyFeedTextBinding

    var listener: ((view: View, item: Feed, position: Int) -> Unit)? = null
    var bookmarkListener: ((item: Feed) -> Unit)? = null
    private val TEXT = 1
    private val IMAGE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TEXT) {
            itemDailyFeedTextBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_daily_feed_text,
                parent,
                false
            )
            return TextViewHolder(itemDailyFeedTextBinding, parent.context)
        } else {
            itemDailyFeedBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_daily_feed,
                parent,
                false
            )
            return ImageViewHolder(itemDailyFeedBinding, parent.context)
        }

    }

    override fun getItemCount(): Int {
        return feedsList.size
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TEXT) {
            (holder as TextViewHolder).bindData(position)
        } else {
            (holder as ImageViewHolder).bindData(position)
        }
    }

    fun addItems(items: List<Feed>) {
        feedsList = items
        notifyDataSetChanged()
    }

    inner class TextViewHolder(val binding: ItemDailyFeedTextBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {

            binding.feed = feedsList[pos]
            binding.txtDate.text = feedsList[pos].createdAt.formatDate()
            binding.root.setOnClickListener {
                listener?.invoke(binding.root, feedsList[pos], pos)
            }
            if (feedsList[pos].isBookmarked) binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)
            else binding.imgBookmark.setBackgroundResource(R.drawable.ic_unbook)

            binding.imgBookmark.setOnClickListener {
                feedsList[pos].isBookmarked = !feedsList[pos].isBookmarked
                notifyItemChanged(pos)
                bookmarkListener?.invoke(feedsList[pos])
            }

        }
    }

    inner class ImageViewHolder(val binding: ItemDailyFeedBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {

            binding.feed = feedsList[pos]
            binding.imgFeed.load(feedsList[pos].backgroundImage.toString())
            binding.txtDate.text = feedsList[pos].createdAt.formatDate()
            binding.root.setOnClickListener {
                listener?.invoke(binding.root, feedsList[pos], pos)
            }
            if (feedsList[pos].isBookmarked) binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)
            else binding.imgBookmark.setBackgroundResource(R.drawable.ic_unbook)

            binding.imgBookmark.setOnClickListener {
                feedsList[pos].isBookmarked = !feedsList[pos].isBookmarked
                notifyItemChanged(pos)
                bookmarkListener?.invoke(feedsList[pos])
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (TextUtils.isEmpty(feedsList[position].backgroundImage) || feedsList[position].backgroundImage == null) {
            TEXT
        } else {
            IMAGE
        }
    }

}