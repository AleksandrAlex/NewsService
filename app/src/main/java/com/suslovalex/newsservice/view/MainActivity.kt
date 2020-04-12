package com.suslovalex.newsservice.view

import Articles
import News
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.suslovalex.newsservice.NewsRecyclerViewAdapter
import com.suslovalex.newsservice.R
import com.suslovalex.newsservice.retrofit.APINews
import com.suslovalex.newsservice.retrofit.NewsClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_service.*

class MainActivity : AppCompatActivity() {

    private lateinit var newsRecyclerViewAdapter: NewsRecyclerViewAdapter
    private var items: ArrayList<Articles> = ArrayList()
    private lateinit var disposable: Disposable
    private lateinit var swipe: SwipeRefreshLayout
    private var thisNews = "TECHNOLOGY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_service)

        prepareSpinner()
        initRecyclerView()
        loadNews()
        prepareSwipe()
    }

    private fun prepareSpinner() {
        val newsBlog = arrayOf("TECHNOLOGY", "SPORT", "SCIENCE", "HEALTH", "ENTERTAINMENT")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, newsBlog)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedNews = parent?.selectedItem.toString()
                Log.d("tag", "$selectedNews")
                setSelectedNews(selectedNews)
                loadNews()
            }
        }
    }

    private fun setSelectedNews(selectedNews: String) {
        thisNews = selectedNews
    }

    private fun prepareSwipe() {
        swipe = swipe_refresh_layout
        swipe.setOnRefreshListener {
            loadNews()
            swipe.isRefreshing = false
        }
    }

    private fun loadNews() {
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
                newsRecyclerViewAdapter.setArticles(it.articles)
                newsRecyclerViewAdapter.notifyDataSetChanged()
            }, {

            })
    }

    private fun initRecyclerView() {
        news_recycler_view.setHasFixedSize(true)
        news_recycler_view.layoutManager = LinearLayoutManager(this)
        newsRecyclerViewAdapter = NewsRecyclerViewAdapter()
        news_recycler_view.adapter = newsRecyclerViewAdapter
        newsRecyclerViewAdapter.setArticles(items)

    }
}

