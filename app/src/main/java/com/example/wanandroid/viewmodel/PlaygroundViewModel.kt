package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaygroundViewModel : DefaultViewModel() {
    private var list = MutableLiveData<ArrayList<Article>>()
    var currentPage: Int = 1
    fun getList() = list
    fun getData(page: Int) {
        appService.getPlayground(page).enqueue(object : Callback<ArticleList> {
            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    list.postValue(body?.data?.datas)

                }
            }

            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
            }

        })
    }

}
