package com.kunal.tnt.createfeed.data

data class CreateFeedResponse(
    val code: Int,
    val data: Data,
    val message: String
)

data class Data(
    val id: String,
    val title: String,
    val category: String,
    val source: String,
    val createdBy: String,
    val createdAt: String,
    val backgroundImage: String
)
