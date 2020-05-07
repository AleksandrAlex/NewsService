package com.suslovalex.newsservice

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.suslovalex.newsservice.data.NewsDAO
import com.suslovalex.newsservice.data.NewsDB
import com.suslovalex.newsservice.model.Article
import com.suslovalex.newsservice.model.News
import com.suslovalex.newsservice.model.Source
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception
import java.security.AccessController.getContext

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var newsDB: NewsDB
    private lateinit var newsDAO: NewsDAO

    @Before
    fun createDB (){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        newsDB = Room.inMemoryDatabaseBuilder(context, NewsDB::class.java)
            .allowMainThreadQueries()
            .build()

        newsDAO = newsDB.newsDAO

    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        newsDB.close()
    }

    @Test
    @Throws(Exception::class)
    fun testDataBase(){
        newsDAO.insetNews(news = News(0,"Status", 1, listOf()))
        val getCount = newsDAO.getCount()
        assertEquals(getCount, 1)
    }



}