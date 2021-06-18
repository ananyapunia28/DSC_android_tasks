package com.example.gazettes.service

import com.example.gazettes.data.NewsApiJSON
import retrofit2.http.GET

interface APIRequest {
    @GET("/v2/top-headlines?country=in&category=business&apiKey=3e84ef4cb4df49759985b7a2ff290e24")
    suspend fun getNews(): NewsApiJSON
}