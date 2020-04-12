package com.suslovalex.newsservice


import News
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.suslovalex.newsservice.data.NewsDB

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var newsLiveData: MutableLiveData<News> = MutableLiveData()
    private val db = NewsDB.getInstance(application)

    init {
        newsLiveData = db?.getNewsDAO()?.getAllNews() as MutableLiveData<News>
    }

    fun getNewsLiveData(): LiveData<News>{
        return newsLiveData
    }


}