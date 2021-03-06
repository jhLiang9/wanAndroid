package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.TreeList
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel

open class SystemViewModel : DefaultViewModel() {
    val list = ArrayList<Tree>()
    val overview = MutableLiveData<TreeList>()
    fun getData() {

        appService.getSystemTree().enqueue(object : retrofit2.Callback<TreeList> {
            override fun onResponse(
                call: retrofit2.Call<TreeList>,
                response: retrofit2.Response<TreeList>
            ) {
                val body = response.body()
                overview.postValue(body!!)
            }

            override fun onFailure(call: retrofit2.Call<TreeList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}