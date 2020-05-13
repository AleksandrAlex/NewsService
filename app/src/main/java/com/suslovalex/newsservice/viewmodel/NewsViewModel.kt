package com.suslovalex.newsservice.viewmodel


import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.suslovalex.newsservice.data.NewsDB
import com.suslovalex.newsservice.model.News
import com.suslovalex.newsservice.repository.NewsRepository
import com.suslovalex.newsservice.retrofit.APINews
import com.suslovalex.newsservice.retrofit.NewsClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var thisNews = "TECHNOLOGY"
    private lateinit var disposable : Disposable
    private var mNews: News? = null

    private val newsRepository: NewsRepository = NewsRepository(application)
    private var news: LiveData<News>

    init {
        news = newsRepository.getAllNews()
        Log.d("viewModel_init_news", "${news.value}")
    }

    fun getNewsLiveData(): LiveData<News> {
        return news
    }

    fun loadNewsToDataBase(selectedBlogNews: String) {
        Log.d("Tag", "fun loadNewsToDataBase()")
        thisNews = selectedBlogNews
        Log.d("load", thisNews)
        val retrofit = NewsClient.instance
        Log.d("load", "$retrofit")
        val articleRetrofit = retrofit.create(APINews::class.java)
        var observable: Observable<News>? = null
        when(thisNews){
            "TECHNOLOGY" -> observable = articleRetrofit.getTechnologyNews()
            "SPORT" -> observable = articleRetrofit.getSportNews()
            "SCIENCE" -> observable = articleRetrofit.getScienceNews()
            "HEALTH" -> observable = articleRetrofit.getHealthNews()
            "ENTERTAINMENT" -> observable = articleRetrofit.getEntertainmentNews()
            else -> Log.d("tag", "error")
        }
        Log.d("load", "$observable")

        disposable = observable!!
            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.newThread())
            .subscribe({
                mNews = it
                removeAllNewsFromDataBase()
                insertNewsToDataBase(it)
//                setNews(it)

                Log.d("load", "fun loadNewsToDataBase ${Gson().toJson(mNews)}")
            }, {
            })
//            Log.d("load", "fun loadNewsToDataBase end1 ${mNews?.let { setNews(it) }}")
            Log.d("load", "fun loadNewsToDataBase end2 ${Gson().toJson(mNews)}")

    }

    private fun insertNewsToDataBase(news: News) {
        newsRepository.insert(news)
    }

    private fun removeAllNewsFromDataBase() {
        newsRepository.deleteAllNews()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}