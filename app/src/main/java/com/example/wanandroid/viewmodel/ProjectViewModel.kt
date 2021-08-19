package com.example.wanandroid.viewmodel


import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.entity.list.TreeList
import com.example.wanandroid.event.ProjectContentEvent
import com.example.wanandroid.event.ProjectListEvent
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.wait
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class ProjectViewModel() : BaseViewModel(){
    var cid = MutableLiveData(294)
    val navList = MutableLiveData<TreeList>()
    val projectList = MutableLiveData<ArticleList>()

    fun initProjectOverview(){
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
                    val gson =Gson()
                    val data = gson.fromJson(responseData, TreeList::class.java)
                    navList.postValue(data)
                }
            })

    }

    fun getProjectContent(url :String){
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //skip
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