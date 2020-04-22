package com.suslovalex.newsservice.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.suslovalex.newsservice.model.Article


class ArticleConverter {

    @TypeConverter
    fun fromListToString(listArticle: List<Article>): String {
        return Gson().toJson(listArticle)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<Article>{
        return Gson().fromJson(string, Array<Article>::class.java).toList()
    }
}