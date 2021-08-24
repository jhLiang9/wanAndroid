package com.example.wanandroid.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.database.SystemDatabase
import com.example.wanandroid.database.dao.SystemDatabaseDao
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.list.TreeList
import com.example.wanandroid.fragment.SystemFragment
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

open class SystemViewModel : BaseViewModel() {
    val overview = MutableLiveData<TreeList>()
    @SuppressLint("UseRequireInsteadOfGet")

     fun getData(){
        val url = "https://www.wanandroid.com/tree/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                val gson = Gson()
                val responseData = response.body?.string()
                val data = gson.fromJson(responseData, TreeList::class.java)
                overview.postValue(data)
            }
        })
    }
}