package com.kunal.tnt.feed.adapter

import com.kunal.tnt.common.adapters.BaseRecyclerViewAdapter
import com.kunal.tnt.common.uils.Utilities.getThumbnailUrl
import com.kunal.tnt.feed.data.Data
import com.kunal.tnt.feed.data.FeedType
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities.loadImage
import com.kunal.tnt.databinding.ItemFeedBinding


class FeedAdapter : BaseRecyclerViewAdapter<Data, ItemFeedBinding>() {

    override fun getLayout(): Int = R.layout.item_feed

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<ItemFeedBinding>, position: Int) {
        val feedResponse = items[position]
       // holder.binding.response = feedResponse

        when (feedResponse.type) {

            FeedType.IMAGE -> {
                holder.binding.imgFeedImage.loadImage(feedResponse.image_url)
            }
            FeedType.VIDEO -> {
                holder.binding.imgFeedImage.loadImage(getThumbnailUrl(feedResponse.video_url))
            }
            else -> holder.binding.imgFeedImage.loadImage("")
        }


        holder.binding.root.setOnClickListener {
            listener?.invoke(it, feedResponse, position)
        }
    }
}