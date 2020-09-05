package com.kunal.tnt.feeddetail

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.databinding.ItemFeedDetailBinding
import com.kunal.tnt.home.data.Feed
import kotlin.math.roundToInt


class FeedDetailAdapter(private val feedsList: List<Feed>) :
    RecyclerView.Adapter<FeedDetailAdapter.FeedViewHolder>() {


    var bookmarkListener: ((item: Feed) -> Unit)? = null
    var shareListener: ((item: Feed) -> Unit)? = null
    var openBrowserListener: ((item: Feed) -> Unit)? = null


    lateinit var binding: ItemFeedDetailBinding

    inner class FeedViewHolder(val binding: ItemFeedDetailBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(feed: Feed, position: Int) {
            binding.feed = feed
            binding.txtName.text =
                context.resources.getString(R.string.by_name, feed.createdBy)

            binding.image.load(feed.backgroundImage.toString())
            // binding.txtDate.text = feedsList[position].createdAt.formatDate()

            if (feed.isBookmarked) binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)
            else binding.imgBookmark.setBackgroundResource(R.drawable.ic_unbook)

            if (TextUtils.isEmpty(feed.backgroundImage) || feed.backgroundImage == null) {
                binding.title.height =
                    context.resources.getDimension(R.dimen.feed_image_height).roundToInt()
                binding.title.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                )
                binding.image.gone()
                binding.viewOverlay.gone()
            } else {
                binding.title.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.title.requestLayout()
                binding.title.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.dark_grey
                    )
                )
                binding.image.visible()
                binding.viewOverlay.visible()
            }

            binding.imgBookmark.setOnClickListener {
                feed.isBookmarked = !feed.isBookmarked
                notifyItemChanged(position)
                bookmarkListener?.invoke(feed)
            }

            binding.imgShare.setOnClickListener {
                shareListener?.invoke(feed)
            }

            binding.imgOpen.setOnClickListener {
                openBrowserListener?.invoke(feed)
            }
        }
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): FeedViewHolder {
        val layoutInflater = LayoutInflater.from(container.context)
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_feed_detail, container, false)
        return FeedViewHolder(binding, container.context)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bindData(feedsList[position], position)
    }

    override fun getItemCount(): Int {
        return feedsList.size
    }

}