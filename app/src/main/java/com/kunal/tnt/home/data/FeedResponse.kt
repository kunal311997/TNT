package com.kunal.tnt.home.data

data class FeedResponse(
    var page: Int,
    var perPage: Int,
    var total: Int,
    var totalPages: Int,
    val feedsList: List<Feed>?
)