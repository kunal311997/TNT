package com.kunal.tnt.createfeed.data

import com.kunal.tnt.feed.data.FeedType

data class CreateFeedRequest(
        val Image_url: String,
        val category: String,
        val description: String,
        val name: String,
        val type: FeedType,
        val user_id: String,
        val video_url: String
)