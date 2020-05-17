package com.suslovalex.newsservice.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.suslovalex.newsservice.model.News
import com.suslovalex.newsservice.repository.NewsRepository


class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var newsLiveData : LiveData<News>

    private val newsRepository: NewsRepository = NewsRepository(application)

    init {
        newsLiveData = newsRepository.getAllNews()
    }

    fun getNewsLiveData(): LiveData<News> {
        return newsLiveData
    }

    fun loadNewsToDataBase(selectedBlogNews: String) {
        newsRepository.loadData(selectedBlogNews)
    }

    override fun onCleared() {
        super.onCleared()
        newsRepository.dispose()
    }
}