package com.example.wanandroid.service

import com.example.wanandroid.entity.list.ArticleList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {
    @GET("article/list/{page}/json")
    fun getArticleData(@Path("page") page: Int):Call<ArticleList>


}