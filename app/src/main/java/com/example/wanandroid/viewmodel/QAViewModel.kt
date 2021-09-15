package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.event.QAEvent
import com.example.wanandroid.service.AppService
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QAViewModel : BaseViewModel() {
    val list = MutableLiveData<ArticleList>()
    var nextPage: Int = 1
    var pageCount = -1
    fun init() {
        getPageByRetrofit(1)
    }

    fun getPageByRetrofit(page: Int) {
        appService.getWenDa(page).enqueue(object : retrofit2.Callback<ArticleList> {
            override fun onResponse(
                call: retrofit2.Call<ArticleList>,
                response: retrofit2.Response<ArticleList>
            ) {
                val body = response.body()
                list.postValue(body)
            }

            override fun onFailure(call: retrofit2.Call<ArticleList>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}