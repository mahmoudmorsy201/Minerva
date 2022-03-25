package com.example.minerva.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Articles",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("email"),
        childColumns = arrayOf("url"),
        onDelete = ForeignKey.CASCADE
    )]
)

data class Article(
    @PrimaryKey
    val url: String,
    val email: String,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val urlToImage: String? = null,
    val isFavourite: Boolean = false
)