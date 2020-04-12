package com.suslovalex.newsservice.data

import News
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [(News::class)], version = 1, exportSchema = false)
abstract class NewsDB: RoomDatabase() {
    abstract fun getNewsDAO(): NewsDAO

    companion object {
        private var INSTANCE: NewsDB? = null

        fun getInstance(context: Context ): NewsDB? {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext
                        , NewsDB::class.java, "news_db"
                    ).build()
                }
                INSTANCE = instance
            }
            return INSTANCE
        }
    }

}