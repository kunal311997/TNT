package com.kunal.tnt.createfeed.data

data class CreateFeedResponse(
    val code: Int,
    val data: Data,
    val message: String
)

data class Data(
    val id: String,
    val title: String,
    val keywords: String,
    val backgroundImage: String
)