package com.suslovalex.newsservice.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suslovalex.newsservice.data.NewsDAO
import com.suslovalex.newsservice.data.NewsDB
import com.suslovalex.newsservice.model.News

class NewsRepository (application: Application ) {

    private val newsDAO : NewsDAO
    private var newsLiveData: LiveData<News>
    private val newsDB: NewsDB = NewsDB.getInstance(application)

    init {
        newsDAO = newsDB.newsDAO()
        newsLiveData = newsDAO.getAllNews()
        Log.d("repository", "${newsDB.toString()}")
    }

    fun getAllNews(): LiveData<News>{
        return newsLiveData
    }

    fun insert (news: News){
        newsDB.newsDAO().insetNews(news)
        Log.d("insert", "${newsLiveData.value}")
    }

    fun deleteAllNews (){
        newsDB.newsDAO().deleteAllNews()
    }

}