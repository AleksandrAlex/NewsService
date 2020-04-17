package com.suslovalex.newsservice.data

import Articles
import News
import Source
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [News::class, Articles::class, Source::class], version = 1, exportSchema = false)
abstract class NewsDB: RoomDatabase() {
    abstract val newsDAO: NewsDAO

    companion object {
        @Volatile
        private var INSTANCE: NewsDB? = null

        fun getInstance(context: Context): NewsDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDB::class.java, "NewsDB"
                    ).allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }

    }
}