package com.suslovalex.newsservice.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.suslovalex.newsservice.NewsRecyclerViewAdapter
import com.suslovalex.newsservice.R
import com.suslovalex.newsservice.model.Article
import com.suslovalex.newsservice.model.News
import com.suslovalex.newsservice.retrofit.APINews
import com.suslovalex.newsservice.retrofit.NewsClient
import com.suslovalex.newsservice.viewmodel.NewsViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_service.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var newsRecyclerViewAdapter: NewsRecyclerViewAdapter
    private var items: List<Article> = mutableListOf()
    private lateinit var swipe: SwipeRefreshLayout
    private var thisNews = "TECHNOLOGY"

    private val newsViewModel by lazy { ViewModelProvider(this).get(NewsViewModel::class.java) }

    private var mNews: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           setContentView(R.layout.news_service)

        initRecyclerView()
        //loadNews()
        subscribeChanges()
        prepareSwipe()
        prepareSpinner()
    }

    private fun loadNews(news: String) {
        Log.d("Tag", "fun loadNews")
        newsViewModel.loadNewsToDataBase(news)

    }

    private fun prepareSpinner() {
        Log.d("Tag", "fun prepareSpinner()")

        val newsBlog = arrayOf("TECHNOLOGY","HEALTH", "SPORT", "SCIENCE", "ENTERTAINMENT")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, newsBlog)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("Tag", "fun prepareSpinner()override fun onNothingSelected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedNews = parent?.selectedItem.toString()
                Log.d("Tag", "fun prepareSpinner()$selectedNews")
                setSelectedNews(selectedNews)
                loadNews(thisNews)
            }
        }

    }

    private fun subscribeChanges() {
        Log.d("Tag", "fun subscribeChanges()")
        newsViewModel.getNewsLiveData().observe(this, Observer {

              it?.let {
                  newsRecyclerViewAdapter.setArticles(it.articles)
                  newsRecyclerViewAdapter.notifyDataSetChanged()

              }

             })
    }

    private fun setSelectedNews(selectedNews: String) {
        thisNews = selectedNews
    }

    private fun prepareSwipe() {
        Log.d("Tag", "fun prepareSwipe()")

        swipe = swipe_refresh_layout
        swipe.setOnRefreshListener {
            loadNews(thisNews)
            newsRecyclerViewAdapter.notifyDataSetChanged()
            swipe.isRefreshing = false
        }

    }

    private fun initRecyclerView() {
        Log.d("Tag", "fun initRecyclerView()")

        news_recycler_view.setHasFixedSize(true)
        news_recycler_view.layoutManager = LinearLayoutManager(this)
        newsRecyclerViewAdapter = NewsRecyclerViewAdapter()
        news_recycler_view.adapter = newsRecyclerViewAdapter
        newsRecyclerViewAdapter.setArticles(items)
    }
}

