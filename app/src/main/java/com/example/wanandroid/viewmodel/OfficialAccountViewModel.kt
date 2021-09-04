package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.entity.list.WXAccountList
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfficialAccountViewModel : BaseViewModel() {
    private var wxArticleList =MutableLiveData<ArticleList>()
    private var wxAccountList =MutableLiveData<WXAccountList>()
    var currentPage =0
    var accountId=-1
    var amount=-1
    fun getAccountList() = wxAccountList
    fun getArticleList() = wxArticleList
     fun getAccounts(){
         appService.getWxAccounts().enqueue(object: Callback<WXAccountList> {
             override fun onResponse(call: Call<WXAccountList>, response: Response<WXAccountList>) {
                 val body=response.body()
                 wxAccountList.postValue(body!!)
             }

             override fun onFailure(call: Call<WXAccountList>, t: Throwable) {
                 t.printStackTrace()
             }

         })
     }

    fun getArticles(id:Int,page:Int){
        appService.getWxArticles(id,page).enqueue(object: Callback<ArticleList> {
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