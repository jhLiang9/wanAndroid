package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WxArticleViewModel : DefaultViewModel() {
    private var wxArticleList = MutableLiveData<ArticleList>()

    var currentPage = 0
    var accountId = -1
    var amount = -1

    fun getArticleList() = wxArticleList

    fun getArticles(id: Int, page: Int) {
        appService.getWxArticles(id, page).enqueue(object : Callback<ArticleList> {
            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                val body = response.body()
                wxArticleList.postValue(body!!)
            }

            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


}