package com.example.wanandroid.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.entity.ArticleList
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Request {
    val client = OkHttpClient()
    val application = WanAndroidApplication
    val appService = ServiceCreator.create(AppService::class.java)

     private fun getArticles(page: Int) {
      //TODO 将请求整合到这里
     }
}