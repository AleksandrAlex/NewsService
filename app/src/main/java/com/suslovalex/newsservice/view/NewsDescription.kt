package com.suslovalex.newsservice.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suslovalex.newsservice.R
import com.suslovalex.newsservice.model.Article
import kotlinx.android.synthetic.main.description_news.*

class NewsDescription : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.description_news)

        val article = intent.getSerializableExtra("intent") as Article
        title_text_view.text = article.title
        name_text_view.text = article.source.name
        description_text_view.text = article.description

    }
}