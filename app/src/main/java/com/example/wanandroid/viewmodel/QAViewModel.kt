package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.data.ArticleData
import com.example.wanandroid.entity.data.QAData
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.entity.list.QAList
import com.example.wanandroid.event.QAEvent
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class QAViewModel: BaseViewModel() {
    val list = MutableLiveData<QAList>()
    var nextPage :Int = 1

    fun init(){getPage(1)}

    fun getPage(page :Int){
        val url = "https://wanandroid.com/wenda/list/$page/json"
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
                val jsonData = JSONObject(responseData).getString("data")
                val datas = JSONObject(jsonData).getString("datas")
                val errorCode = JSONObject(responseData).getInt("errorCode")
                val errorMessage = JSONObject(responseData).getString("errorMsg")
                val curPage= JSONObject(jsonData).getInt("curPage")
                val qaList = ArrayList<Article>()
                val jsonArray = JSONArray(datas)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val title = jsonObject.getString("title")
                    val time = jsonObject.getString("niceDate")
                    var author = jsonObject.getString("author")
                    if (author == "") {
                        author = jsonObject.getString("shareUser")
                    }
                    val superChapterName = jsonObject.getString("superChapterName")
                    val link = jsonObject.getString("link")
                    val description = jsonObject.getString("desc")
                    val id = jsonObject.getInt("id")
                    qaList.add(Article(id,title, author,time, superChapterName,link,description))
                }
                val over =  JSONObject(jsonData).getBoolean("over")
                val pageCount = JSONObject(jsonData).getInt("pageCount")
                val size =JSONObject(jsonData).getInt("size")
                val total =JSONObject(jsonData).getInt("total")
                list.postValue(QAList(QAData(curPage,qaList,over,pageCount, size, total),errorCode,errorMessage ))
            }
        })
    }
}