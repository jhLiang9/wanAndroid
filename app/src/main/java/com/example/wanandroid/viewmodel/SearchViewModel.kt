package com.example.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : DefaultViewModel() {
    private val _articleList = MutableLiveData<ArticleList>()
    val articleList: LiveData<ArticleList> = _articleList

    fun search(page: Int, keyword: String) {
        appService.search(page, keyword).enqueue(object : Callback<ArticleList> {
            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                _articleList.postValue(response.body())
            }

            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}