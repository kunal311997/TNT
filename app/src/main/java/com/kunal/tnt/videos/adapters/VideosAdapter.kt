package com.kunal.tnt.videos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunal.tnt.R
import com.kunal.tnt.createfeed.data.Keywords
import com.kunal.tnt.databinding.ItemVideosBinding
import com.kunal.tnt.videos.models.Video
import com.kunal.tnt.videos.models.VideosResponse

class VideosAdapter(
    var videosList: List<Video>
) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    lateinit var binding: ItemVideosBinding
    var listener: ((view: View, item: Keywords, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_videos,
            parent,
            false
        )
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    fun addItems(items: List<Video>) {
        videosList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemVideosBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {
            binding.video = videosList[pos]
            val adapter = VideosSubAdapter(videosList[pos].item)
            binding.rvSubVideo.adapter = adapter
        }
    }

}