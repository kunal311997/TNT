package com.kunal.tnt.home.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Feed(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val source: String,
    val createdBy: String,
    val backgroundImage: String?,
    val createdAt: String,
    var isBookmarked: Boolean = false
) : Parcelable