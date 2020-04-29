package com.suslovalex.newsservice.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.suslovalex.newsservice.R
import com.suslovalex.newsservice.model.Article
import kotlinx.android.synthetic.main.description_news.*

class NewsDescription : AppCompatActivity() {

    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.description_news)

        getArticle()
        setValues()

    }

    private fun setValues() {
        title_text_view.text = article.description

        Glide.with(this).load(article.urlToImage).into(image_view)


    }

    private fun getArticle() {
         article = intent.getSerializableExtra("intent") as Article
    }
}