package com.suslovalex.newsservice.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.suslovalex.newsservice.model.News

@Dao
interface NewsDAO {
    @Query ("SELECT * FROM news")
    fun getAllNews(): LiveData<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetNews(news: News)

    @Query("DELETE FROM news")
    fun deleteAllNews()

    @Query ("SELECT COUNT (*) FROM news")
    fun getCount(): Int


}