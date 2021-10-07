package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel

class QAViewModel : BaseViewModel() {
    val list = MutableLiveData<ArticleList>()
    var nextPage = 1
    var pageCount = -1

    fun init() = getPageByRetrofit(1)

    fun getPageByRetrofit(page: Int) {
        appService.getWenDa(page).enqueue(object : retrofit2.Callback<ArticleList> {
            override fun onResponse(
                call: retrofit2.Call<ArticleList>,
                response: retrofit2.Response<ArticleList>
            ) {
                val body = response.body()
                if (body != null) {
                    pageCount = body.data.pageCount
                    list.postValue(body)
                }
            }

            override fun onFailure(call: retrofit2.Call<ArticleList>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
    fun getNextPage(){
        getPageByRetrofit(nextPage)
        nextPage++
    }
    fun hasNextPage():Boolean{
        return nextPage < pageCount
    }
}