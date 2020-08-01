package com.kunal.tnt.feed.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FeedResponse(
        @SerializedName("code") val code: Int,
        @SerializedName("data") val data: List<Data>,
        @SerializedName("message") val message: String
) : Serializable

data class Data(
        @SerializedName("category") val category: String,
        @SerializedName("name") val name: String,
        @SerializedName("date") val date: String,
        @SerializedName("description") val description: String,
        @SerializedName("id") val id: String,
        @SerializedName("image_url") val image_url: String,
        @SerializedName("type") val type: FeedType,
        @SerializedName("user_id") val user_id: String,
        @SerializedName("video_url") val video_url: String
) : Serializable