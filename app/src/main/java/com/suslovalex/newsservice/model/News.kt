package com.suslovalex.newsservice.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "news")
data class News(
    @PrimaryKey (autoGenerate = false)
    val id: Int = 0,
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
): Serializable