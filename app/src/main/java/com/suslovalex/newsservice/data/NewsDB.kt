package com.suslovalex.newsservice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suslovalex.newsservice.converter.ArticleConverter
import com.suslovalex.newsservice.model.News


@Database(entities = [News::class], version = 1, exportSchema = false)
@TypeConverters(ArticleConverter::class)
abstract class NewsDB: RoomDatabase() {
    abstract fun newsDAO(): NewsDAO

    companion object {
        @Volatile
        private var INSTANCE: NewsDB? = null

        fun getInstance(context: Context): NewsDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDB::class.java,
                    "news_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }

        }

    }
}