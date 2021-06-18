package com.example.gazettes.adapter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gazettes.R
import com.example.gazettes.UI.MainFragment
import com.example.gazettes.UI.WebFragment
import com.example.gazettes.caching.InternetConnection
import com.example.gazettes.data.Article
import com.google.android.material.snackbar.Snackbar

class NewsAdapter(private var titles: List<String>,
                  private var details: List<String>,
                  private var images: List<String>,
                  private var links: List<String>,
                  private val pubdate:List<String>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>(){
//    private lateinit var navController: NavController

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemTitle: TextView = itemView.findViewById(R.id.news)
        val itemAuthor: TextView = itemView.findViewById(R.id.author)
        val itemPicture: ImageView = itemView.findViewById(R.id.img)
        val publishedate: TextView? = null


        init {
            itemView.setOnClickListener { v: View ->

            }
        }




    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ArticleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_lay, parent, false)
        Log.d("data", pubdate.toString())
        return ArticleViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ArticleViewHolder, position: Int) {

        holder.itemTitle.text = titles[position]
        holder.itemAuthor.text = details[position]

        Glide.with(holder.itemPicture)
            .load(images[position])
            .into(holder.itemPicture)
        holder.itemView.setOnLongClickListener{
            val dataTime: String = pubdate[position]
            Snackbar.make(it,"This news was published at $dataTime hours",Snackbar.LENGTH_SHORT).show()
            true
        }
        holder.itemView.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
//            it.findNavController().navigate(R.id.action_mainFragment_to_webFragment)
                startActivity(it.context, intent, null)

        }


    }

    override fun getItemCount(): Int {
        return titles.size

    }


}

