package com.example.wanandroid.viewmodel


import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.TreeList
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class ProjectViewModel : DefaultViewModel() {
    var cid = MutableLiveData(294)
    var leftList = ArrayList<Tree>()
    var rightList = ArrayList<Article>()
    val navList = MutableLiveData<TreeList>()
    val projectList = MutableLiveData<ArticleList>()


    fun initProjectOverview() {
        val url = "https://www.wanandroid.com/project/tree/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body!!.string()
                val gson = Gson()
                val data = gson.fromJson(responseData, TreeList::class.java)
                navList.postValue(data)
            }
        })

    }

    fun getProjectContent(cid: Int, page: Int) {
        val url = "https://www.wanandroid.com/project/list/$page/json?cid=$cid"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val gson = Gson()
                val data = gson.fromJson(responseData, ArticleList::class.java)
                projectList.postValue(data)
            }
        })
    }
}