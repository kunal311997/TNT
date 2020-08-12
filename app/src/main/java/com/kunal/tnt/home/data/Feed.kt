package com.kunal.tnt.home.data

data class Feed(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val source: String,
    val createdBy: String,
    val backgroundImage: String?,
    val createdAt: String
)