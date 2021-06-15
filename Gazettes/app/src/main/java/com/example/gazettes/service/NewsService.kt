package com.example.gazettes.service

import com.example.gazettes.data.MyNews
import okhttp3.*
import retrofit2.Call
import android.content.Context
import com.example.gazettes.caching.Connection.checknetwork
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=3e84ef4cb4df49759985b7a2ff290e24
//https://newsapi.org/v2/everything?q=tesla&from=2021-05-09&sortBy=publishedAt&apiKey=3e84ef4cb4df49759985b7a2ff290e24

const val base_url ="https://newsapi.org"
const val api_key = "3e84ef4cb4df49759985b7a2ff290e24"

interface NewsInterface {
        @GET("/v2/top-headlines&apiKey=$api_key")
        fun getHeadline(@Query("country")country : String, @Query("page")page: Int): Call<MyNews>
}
object NewsService{


    val NewsInstance : NewsInterface
    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        NewsInstance = retrofit.create(NewsInterface::class.java)

    }
}