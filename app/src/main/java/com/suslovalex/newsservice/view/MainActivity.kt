package com.suslovalex.newsservice.view

import Articles
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.suslovalex.newsservice.NewsRecyclerViewAdapter
import com.suslovalex.newsservice.R
import com.suslovalex.newsservice.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.news_service.*

class MainActivity : AppCompatActivity() {

    private lateinit var newsRecyclerViewAdapter: NewsRecyclerViewAdapter
    private var items: ArrayList<Articles> = ArrayList()
    private lateinit var swipe: SwipeRefreshLayout
    private var thisNews = "TECHNOLOGY"
    private val newsViewModel by lazy {
        ViewModelProvider(this)
            .get(NewsViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_service)

       // newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        prepareSpinner()
        initRecyclerView()
        subscribeChanges()
        //loadNews()
        prepareSwipe()
    }

    private fun loadNews(news: String) {
        newsViewModel.loadNewsFromDataBase(news)
    }

    private fun subscribeChanges() {
        newsViewModel.getNewsLiveData().observe(this, Observer {
            it.let {
                newsRecyclerViewAdapter.setArticles(it.articles)
            } })
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
                loadNews(thisNews)
            }
        }
    }

    private fun setSelectedNews(selectedNews: String) {
        thisNews = selectedNews
    }

    private fun prepareSwipe() {
        swipe = swipe_refresh_layout
        swipe.setOnRefreshListener {
            loadNews(thisNews)
            swipe.isRefreshing = false
        }
    }



    private fun initRecyclerView() {
        news_recycler_view.setHasFixedSize(true)
        news_recycler_view.layoutManager = LinearLayoutManager(this)
        newsRecyclerViewAdapter = NewsRecyclerViewAdapter()
        news_recycler_view.adapter = newsRecyclerViewAdapter
        newsRecyclerViewAdapter.setArticles(items)

    }
}

