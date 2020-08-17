package com.kunal.tnt.videos.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.databinding.SubItemVideosBinding
import com.kunal.tnt.videos.models.Video
import com.kunal.tnt.videos.models.VideoItem
import com.kunal.tnt.videos.ui.VideoViewActivity

class VideosSubAdapter(
    var videosList: List<VideoItem>
) :
    RecyclerView.Adapter<VideosSubAdapter.ViewHolder>() {

    lateinit var binding: SubItemVideosBinding
    var listener: ((view: View, item: Video, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.sub_item_videos,
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

    fun addItems(items: List<VideoItem>) {
        videosList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: SubItemVideosBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(
            pos: Int
        ) {
            binding.subVideo = videosList[pos]
            binding.image.load("https://i.ytimg.com/vi/${videosList[pos].videoCode}/hqdefault.jpg")
            binding.videoSubItem.setOnClickListener {
 
                val intent = Intent(context, VideoViewActivity::class.java)
                intent.putExtra("videoCode", videosList[pos].videoCode)
                context.startActivity(intent)
            }
        }
    }

}