package com.suslovalex.newsservice

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.suslovalex.newsservice.databinding.ItemNewsBinding
import com.suslovalex.newsservice.model.Article
import com.suslovalex.newsservice.view.NewsDescription
import kotlinx.android.synthetic.main.item_news.view.*


class NewsRecyclerViewAdapter(): RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    private lateinit var items: List<Article>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder (
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Article = items[position]
        holder.clickOnArticle(article)
        holder.binding.article = article
    }

    fun setArticles(articlesList: List<Article>) {
        items = articlesList
        Log.d("Tag", "fun setArticles() $items")
    }

        class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun clickOnArticle(article: Article) {

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDescription::class.java)
                intent.putExtra("intent", article)
                itemView.context.startActivity(intent)
            }
        }

    }
}