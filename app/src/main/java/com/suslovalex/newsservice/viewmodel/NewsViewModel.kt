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
import com.suslovalex.newsservice.retrofit.APINews
import com.suslovalex.newsservice.retrofit.NewsClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var thisNews = "TECHNOLOGY"
    private var compositeDisposable = CompositeDisposable()
    private val db: NewsDB = NewsDB.getInstance(application.applicationContext)
    private var newsLiveData = db.newsDAO.getAllNews()
    private var mNews: News? = null

//    init {
//        newsLiveData = db.newsDAO.getAllNews()
//    }

//    private fun setNews(news: News){
//        newsLiveData.value = news
//        Log.d("load", "")
//    }

    fun getNewsLiveData(): LiveData<News> {
        Log.d("Tag", "fun getNewsLiveData()${newsLiveData.value}")
        newsLiveData = db.newsDAO.getAllNews()
        return newsLiveData
    }

    private fun insertNewsToDataBase(news: News){
        db.newsDAO.insetNews(news)
       // setNews(news)
    }

    private fun removeAllNewsFromDataBase(){
       // newsLiveData.postValue(null)
        db.newsDAO.deleteAllNews()

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

        var disposable = observable!!
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                removeAllNewsFromDataBase()
                insertNewsToDataBase(it)

                Log.d("load", "fun loadNewsToDataBase ${Gson().toJson(mNews)}")
            }, {
            })
//            Log.d("load", "fun loadNewsToDataBase end1 ${mNews?.let { setNews(it) }}")
            Log.d("load", "fun loadNewsToDataBase end2 ${Gson().toJson(mNews)}")
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}