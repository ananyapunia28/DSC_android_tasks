package com.example.gazettes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gazettes.R
import com.example.gazettes.UI.MainFragment
import com.example.gazettes.data.Article
import com.google.android.material.snackbar.Snackbar

class NewsAdapter(val context: MainFragment, val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_lay, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article: Article = articles[position]
        holder.newsHeadline.text = article.title
        holder.newsAuthor.text = article.author
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)


        holder.itemView.setOnLongClickListener{
            var times = articles[position].publishedAt.replace('T',' ').replace('Z',' ')
            Snackbar.make(it,"This news was published at ${times}",Snackbar.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
        

    }

    override fun getItemCount(): Int {
       return articles.size
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage = itemView.findViewById<ImageView>(R.id.img)
        var newsHeadline = itemView.findViewById<TextView>(R.id.news)
        var newsAuthor = itemView.findViewById<TextView>(R.id.author)

    }

}
