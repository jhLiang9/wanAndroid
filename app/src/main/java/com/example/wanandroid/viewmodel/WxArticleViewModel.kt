package com.example.wanandroid.viewmodel

import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WxArticleViewModel : BaseViewModel() {

    fun getArticles(id: Int, page: Int):ArticleList{
        val call =appService.getWxArticles(id,page)
        lateinit var res:ArticleList
        call.enqueue(object: Callback<ArticleList>{
            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                res = response.body()!!
            }

            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return res
    }
}