package com.example.wanandroid.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.data.ArticleData
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.event.HomePageDataReadyEvent
import com.example.wanandroid.utils.HtmlElementUtil
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

open class HomePageViewModel : ViewModel() {
    private val client = OkHttpClient()
    private lateinit var set : HashSet<Int>
    val list = MutableLiveData<ArticleList>()

    fun getArticlesByPage(page: Int) {
        val url = "https://www.wanandroid.com/article/list/$page/json"
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
                val jsondata = JSONObject(responseData).getString("data")
                val curPage= JSONObject(jsondata).getInt("curPage")
                val rawDatas = JSONObject(jsondata).getString("datas")
                    val datas = JSONArray(rawDatas)
                    val data = ArrayList<Article>()
                    for(i in 0 until datas.length()){
                        val jsonObject = datas.getJSONObject(i)
                        var title = HtmlElementUtil.removeHTMLTag(jsonObject.getString("title"))
                        if (title == "") {
                            title = jsonObject.getString("title")
                        }
                        val time = jsonObject.getString("niceDate")
                        var author = jsonObject.getString("author")
                        if (author == "") {
                            author = jsonObject.getString("shareUser")
                        }
                        val superChapterName = jsonObject.getString("superChapterName")
                        val link = jsonObject.getString("link")
                        val id = jsonObject.getInt("id")
                        data.add(Article(id,title,author,time,superChapterName,link))
                    }
                val over =  JSONObject(jsondata).getBoolean("over")
                val pageCount = JSONObject(jsondata).getInt("pageCount")
                val size =JSONObject(jsondata).getInt("size")
                val total =JSONObject(jsondata).getInt("total")
                list.postValue(ArticleList(ArticleData(curPage,data,over,pageCount,size,total )))
            }
        })
    }


}