package com.suslovalex.newsservice.viewmodel


import News
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.suslovalex.newsservice.data.NewsDB
import com.suslovalex.newsservice.retrofit.APINews
import com.suslovalex.newsservice.retrofit.NewsClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var newsLiveData: MutableLiveData<News>
    private var thisNews = "TECHNOLOGY"
    private lateinit var disposable: Disposable
    private val db: NewsDB

    init {
        db = NewsDB.getInstance(application)
        newsLiveData = db.newsDAO.getAllNews() as MutableLiveData<News>
    }

    fun getNewsLiveData(): LiveData<News> = newsLiveData

    private fun insertNewsToDataBase(news: News){
        db.newsDAO.insetNews(news)
    }

    private fun removeAllNewsFromDataBase(){
        db.newsDAO.deleteAllNews()
    }

    fun loadNewsFromDataBase(selectedBlogNews: String) {
        thisNews = selectedBlogNews
        val retrofit = NewsClient.instance
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
        disposable = observable!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                removeAllNewsFromDataBase()
                insertNewsToDataBase(it)
                newsLiveData.postValue(it)
            }, {

            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }



}