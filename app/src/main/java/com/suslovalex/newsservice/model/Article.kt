package com.suslovalex.newsservice.model


import androidx.room.Embedded
import java.io.Serializable

data class Article(
    @Embedded val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
): Serializable