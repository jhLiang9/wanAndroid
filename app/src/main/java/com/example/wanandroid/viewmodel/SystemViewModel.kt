package com.example.wanandroid.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.entity.list.TreeList
import com.example.wanandroid.service.AppService
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class SystemViewModel : BaseViewModel() {
    val list = ArrayList<Tree>()
    val overview = MutableLiveData<TreeList>()
    fun getData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        appService.getSystemTree().enqueue(object : retrofit2.Callback<TreeList> {
            override fun onResponse(
                call: retrofit2.Call<TreeList>,
                response: retrofit2.Response<TreeList>
            ) {
                val body = response.body()
                overview.postValue(body)
            }

            override fun onFailure(call: retrofit2.Call<TreeList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}