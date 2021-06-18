package com.example.gazettes.data

data class NewsApiJSON(
    val articles: List<ArticleX>,
    val status: String,
    val totalResults: Int
)