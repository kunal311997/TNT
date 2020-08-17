package com.kunal.tnt.videos.models

data class Video(
    val id: String,
    val heading: String,
    val item: List<VideoItem>
)

data class VideoItem(
    val title: String,
    val videoCode: String
)
