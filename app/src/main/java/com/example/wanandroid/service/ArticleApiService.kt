package com.example.wanandroid.service

import com.example.wanandroid.entity.Article
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


import retrofit2.http.GET

private const val BASE_URL="https://www.wanandroid.com/"
private val retrofit=Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ArticleApiService {
    @GET("article/list/0/json")
    fun getArticle(): Call<String>
}
object ArticleApi{
    val retrofitService :ArticleApiService by lazy{
        retrofit.create(ArticleApiService::class.java)
    }
}