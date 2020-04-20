package com.suslovalex.newsservice

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suslovalex.newsservice.model.Article
import com.suslovalex.newsservice.view.NewsDescription
import kotlinx.android.synthetic.main.item_news.view.*


class NewsRecyclerViewAdapter(): RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    private lateinit var items: List<Article>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Article = items[position]
        holder.bind(article)
    }

    fun setArticles(articlesList: List<Article>) {
        items = articlesList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val authorText: TextView = itemView.author_text_view
        private val titleText: TextView = itemView.title_text_view
        private val publishedText: TextView = itemView.publishedAt_text_view


        fun bind(article: Article) {
            val author = "Author: " + article.author
            authorText.text = author
            val title = "Article: " + article.title
            titleText.text = title
            val published = "Published at: " + article.publishedAt
            publishedText.text = published

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDescription::class.java)
                intent.putExtra("intent", article)
                itemView.context.startActivity(intent)
            }
        }

    }
}