package com.suslovalex.newsservice.data

import News
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDAO {
    @Query ("SELECT * FROM news")
    fun getAllNews(): LiveData<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetNews(news: News)

    @Query("DELETE FROM news")
    fun deleteAllNews()


}