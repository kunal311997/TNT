package com.kunal.tnt.createfeed.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.kunal.tnt.feed.data.FeedType
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class CreateFeedResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: Data,
    @SerializedName("message") val message: String
) : Parcelable

@Parcelize
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
) : Parcelable