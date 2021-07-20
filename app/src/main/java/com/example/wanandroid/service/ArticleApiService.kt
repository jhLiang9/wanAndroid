package com.example.wanandroid.service

import com.example.wanandroid.entity.Article
import retrofit2.Call

import retrofit2.http.GET

interface ArticleApiService {
    @GET("")
    fun getProject(): Call<List<Article>>
}