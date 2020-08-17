package com.kunal.tnt.videos.models

data class VideosResponse(
    val code: Int,
    val message: String,
    val data: List<Video>
)


