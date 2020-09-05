package com.kunal.tnt.home.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities.formatDate
import com.kunal.tnt.databinding.ItemDailyFeedBinding
import com.kunal.tnt.databinding.ItemDailyFeedTextBinding
import com.kunal.tnt.home.data.Feed

class FeedsAdapter : PagingDataAdapter<Feed, RecyclerView.ViewHolder>(FeedComparator) {

    private lateinit var itemDailyFeedBinding: ItemDailyFeedBinding
    private lateinit var itemDailyFeedTextBinding: ItemDailyFeedTextBinding

    var hashMap: HashSet<String>? = null

    var listener: ((view: View, item: Feed, position: Int) -> Unit)? = null
    var bookmarkListener: ((item: Feed,pos:Int) -> Unit)? = null
    var shareListener: ((item: Feed) -> Unit)? = null
    var openBrowserListener: ((item: Feed) -> Unit)? = null

    private val TEXT = 1
    private val IMAGE = 2

    object FeedComparator : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem == newItem
        }
    }

    fun addDbHashMap(hashMap: HashSet<String>?) {
        this.hashMap = hashMap
    }


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

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TEXT) {
            (holder as TextViewHolder).bindData(position)
        } else {
            (holder as ImageViewHolder).bindData(position)
        }
    }

    inner class TextViewHolder(val binding: ItemDailyFeedTextBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {

            val item = getItem(pos)
            item?.let {
                item.isBookmarked = hashMap?.contains(item.id) == true
                binding.feed = item
                binding.txtDate.text = item.createdAt.formatDate()
                binding.root.setOnClickListener {
                    listener?.invoke(binding.root, item, pos)
                }
                if (item.isBookmarked) binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)
                else binding.imgBookmark.setBackgroundResource(R.drawable.ic_unbook)

                binding.imgBookmark.setOnClickListener {

                    if (item.isBookmarked) {
                        hashMap?.remove(item.id)
                    } else hashMap?.add(item.id)

                    item.isBookmarked = !item.isBookmarked
                    notifyItemChanged(pos)

                    bookmarkListener?.invoke(item,pos)
                }
                binding.imgShare.setOnClickListener {
                    shareListener?.invoke(item)
                }

                binding.imgOpen.setOnClickListener {
                    openBrowserListener?.invoke(item)
                }
            }

        }
    }

    inner class ImageViewHolder(val binding: ItemDailyFeedBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {

            val item = getItem(pos)
            item?.let {
                item.isBookmarked = hashMap?.contains(item.id) == true
                binding.feed = item
                binding.imgFeed.load(item.backgroundImage.toString())
                binding.txtDate.text = item.createdAt.formatDate()
                binding.root.setOnClickListener {
                    listener?.invoke(binding.root, item, pos)
                }
                if (item.isBookmarked) binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)
                else binding.imgBookmark.setBackgroundResource(R.drawable.ic_unbook)

                binding.imgBookmark.setOnClickListener {

                    if (item.isBookmarked) {
                        hashMap?.remove(item.id)
                    } else hashMap?.add(item.id)

                    item.isBookmarked = !item.isBookmarked
                    notifyItemChanged(pos)
                    bookmarkListener?.invoke(item,pos)
                }

                binding.imgShare.setOnClickListener {
                    shareListener?.invoke(item)
                }

                binding.imgOpen.setOnClickListener {
                    openBrowserListener?.invoke(item)
                }
            }


        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (TextUtils.isEmpty(getItem(position)?.backgroundImage) || getItem(position)?.backgroundImage == null) {
            TEXT
        } else {
            IMAGE
        }
    }

}