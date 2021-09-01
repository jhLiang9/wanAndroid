package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfficialAccountViewModel : BaseViewModel() {
    private var wxArticleList =MutableLiveData<ArticleList>()

    var currentPage =0
    fun getList() = wxArticleList

     fun getData(page:Int){
         appService.getWXArticles().enqueue(object: Callback<ArticleList> {
             override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                 val body=response.body()
                 wxArticleList.postValue(body!!)
             }

             override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                 t.printStackTrace()
             }

         })
     }
}