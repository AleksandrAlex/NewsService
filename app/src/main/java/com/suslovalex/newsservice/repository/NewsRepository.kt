package com.suslovalex.newsservice.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.suslovalex.newsservice.data.NewsDAO
import com.suslovalex.newsservice.data.NewsDB
import com.suslovalex.newsservice.model.News
import com.suslovalex.newsservice.retrofit.APINews
import com.suslovalex.newsservice.retrofit.NewsClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsRepository (application: Application ) {
    private lateinit var disposable : Disposable
    private val newsDAO : NewsDAO
    private var newsLiveData : LiveData<News>
    private val newsDB: NewsDB = NewsDB.getInstance(application)
    private var mNews: News? = null


    init {
        newsDAO = newsDB.newsDAO()
        newsLiveData = newsDAO.getAllNews()
        Log.d("repository", "${newsDB.toString()}")
    }

    fun getAllNews(): LiveData<News>{
        return newsLiveData
    }

    private fun insertNews (news: News){
        newsDB.newsDAO().insetNews(news)
        Log.d("insert", "${newsLiveData.value}")
    }

    private fun deleteAllNews (){
        newsDB.newsDAO().deleteAllNews()
    }

    fun loadData(news: String) {
        Log.d("load", news)
        val retrofit = NewsClient.instance
        Log.d("load", "$retrofit")
        val articleRetrofit = retrofit.create(APINews::class.java)
        var observable: Observable<News>? = null
        when(news){
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
            //.delay(3, TimeUnit.SECONDS)
//            .replay()
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                deleteAllNews()
                insertNews(it)
//                newsLiveData.value = it
                mNews = it

                Log.d("load", "fun loadNewsToDataBase ${Gson().toJson(mNews)}")
            }, {
                //Toast.makeText(getApplication(),"ERROR!", Toast.LENGTH_LONG).show()
            })
        Log.d("load", "fun loadNewsToDataBase end2 ${Gson().toJson(mNews)}")
    }

    fun dispose(){
        disposable.dispose()
    }
}