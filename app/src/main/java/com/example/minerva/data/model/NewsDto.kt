package com.example.minerva.data.model


data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int,
)