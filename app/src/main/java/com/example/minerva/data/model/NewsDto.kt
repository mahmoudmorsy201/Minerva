package com.example.minerva.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class NewsDto(
    val articles: List<Article>,
    @PrimaryKey
    val status: String,
    val totalResults: Int,
)