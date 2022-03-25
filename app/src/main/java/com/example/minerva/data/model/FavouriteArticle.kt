package com.example.minerva.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Favourites")
data class FavouriteArticle(
    val author: Any? = null,
    val content: Any? = null,
    val description: Any? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    @PrimaryKey
    val url: String? = null,
    val urlToImage: Any? = null
)
