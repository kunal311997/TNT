package com.kunal.tnt.favourites.adapters

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
import com.kunal.tnt.databinding.ItemFavouriteImageBinding
import com.kunal.tnt.databinding.ItemFavouriteTextBinding
import com.kunal.tnt.favourites.models.Favourites

class FavouritesAdapter(
    private var feedsList: List<Favourites>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemFavouriteImageBinding: ItemFavouriteImageBinding
    private lateinit var itemFavouriteTextBinding: ItemFavouriteTextBinding

    var listener: ((view: View, item: Favourites, position: Int) -> Unit)? = null
    var bookmarkListener: ((item: Favourites, pos: Int) -> Unit)? = null
    private val TEXT = 1
    private val IMAGE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TEXT) {
            itemFavouriteTextBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favourite_text, parent, false
            )
            return TextViewHolder(itemFavouriteTextBinding, parent.context)
        } else {
            itemFavouriteImageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_favourite_image, parent, false
            )
            return ImageViewHolder(itemFavouriteImageBinding, parent.context)
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

    fun addItems(items: List<Favourites>) {
        feedsList = items
        notifyDataSetChanged()
    }

    inner class TextViewHolder(val binding: ItemFavouriteTextBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {
            binding.txtName.text = feedsList[pos].createdBy
            binding.txtTitle.text = feedsList[pos].title
            binding.txtDesc.text = feedsList[pos].description
            binding.txtDate.text = feedsList[pos].createdAt.formatDate()
            binding.root.setOnClickListener {
                listener?.invoke(binding.root, feedsList[pos], pos)
            }
            binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)

            binding.imgBookmark.setOnClickListener {
                feedsList[pos].isBookmarked = !feedsList[pos].isBookmarked
                notifyItemChanged(pos)
                bookmarkListener?.invoke(feedsList[pos], pos)
            }
        }
    }

    inner class ImageViewHolder(val binding: ItemFavouriteImageBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {
            binding.txtName.text = feedsList[pos].createdBy
            binding.txtTitle.text = feedsList[pos].title
            binding.txtDesc.text = feedsList[pos].description
            binding.imgFeed.load(feedsList[pos].backgroundImage.toString())
            binding.txtDate.text = feedsList[pos].createdAt.formatDate()
            binding.root.setOnClickListener {
                listener?.invoke(binding.root, feedsList[pos], pos)
            }
            binding.imgBookmark.setBackgroundResource(R.drawable.ic_book)

            binding.imgBookmark.setOnClickListener {
                feedsList[pos].isBookmarked = !feedsList[pos].isBookmarked
                notifyItemChanged(pos)
                bookmarkListener?.invoke(feedsList[pos], pos)
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