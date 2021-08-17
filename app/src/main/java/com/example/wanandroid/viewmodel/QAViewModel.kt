package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.event.QAEvent
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class QAViewModel: BaseViewModel() {
    val list = MutableLiveData<ArticleList>()
    var nextPage :Int = 1

    fun init(){getPage(1)}

    fun getPage(page :Int){
        val url = "https://wanandroid.com/wenda/list/$page/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body!!.string()
                val gson = Gson()
                val data = gson.fromJson(responseData, ArticleList::class.java)
                list.postValue(data)
            }
        })
    }
}