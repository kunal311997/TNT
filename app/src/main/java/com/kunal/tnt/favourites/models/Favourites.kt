package com.kunal.tnt.favourites.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Favourites(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "source")
    val source: String,

    @ColumnInfo(name = "createdBy")
    val createdBy: String,

    @ColumnInfo(name = "backgroundImage")
    val backgroundImage: String?,

    @ColumnInfo(name = "createdAt")
    val createdAt: String,

    @ColumnInfo(name = "isBookmarked")
    var isBookmarked: Boolean = false
)