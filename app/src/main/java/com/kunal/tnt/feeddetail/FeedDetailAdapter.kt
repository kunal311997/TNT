package com.kunal.tnt.feeddetail

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities.formatDate
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.databinding.ItemFeedDetailBinding
import com.kunal.tnt.home.data.Feed
import kotlin.math.roundToInt


class FeedDetailAdapter(private val feedsList: List<Feed>) : PagerAdapter() {

    override fun getCount(): Int {
        return feedsList.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val binding: ItemFeedDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_feed_detail, container, false)

        binding.feed = feedsList[position]
        binding.txtName.text =
            container.context.resources.getString(R.string.by_name, feedsList[position].createdBy)

        binding.image.load(feedsList[position].backgroundImage.toString())
        binding.txtDate.text = feedsList[position].createdAt.formatDate()

        if (feedsList[position].isBookmarked) binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)
        else binding.imgBookmark.setBackgroundResource(R.drawable.ic_unbook)

        if (TextUtils.isEmpty(feedsList[position].backgroundImage) || feedsList[position].backgroundImage == null) {
            binding.title.height =
                container.context.resources.getDimension(R.dimen.feed_image_height).roundToInt()
            binding.title.setBackgroundColor(
                ContextCompat.getColor(
                    container.context,
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
                    container.context,
                    R.color.dark_grey
                )
            )
            binding.image.visible()
            binding.viewOverlay.visible()
        }

        container.addView(binding.root, 0)
        return binding.root
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

}