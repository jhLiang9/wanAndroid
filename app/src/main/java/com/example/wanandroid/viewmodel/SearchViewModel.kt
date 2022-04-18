package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : DefaultViewModel() {
    val list = ArrayList<Article>()
    var articleList = MutableLiveData<ArticleList>()

    fun search(page: Int, keyword: String) {
        appService.search(page, keyword).enqueue(object : Callback<ArticleList> {
            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {

                articleList.postValue(response.body())
            }

            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}