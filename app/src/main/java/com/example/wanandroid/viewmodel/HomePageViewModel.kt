package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.data.ArticleData
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.utils.HtmlElementUtil
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

open class HomePageViewModel : BaseViewModel() {

    private lateinit var set : HashSet<Int>
    val articleList = MutableLiveData<ArticleList>()
    //下一页
    var nextPage :Int = 1

    fun refresh(){
        nextPage =1
        getArticlesByPage(0)
    }

    fun getArticlesByPage(page: Int) {
        val url = "https://www.wanandroid.com/article/list/$page/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)

        viewModelScope.launch {
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body!!.string()
                    val jsondata = JSONObject(responseData).getString("data")
                    val errorCode = JSONObject(responseData).getInt("errorCode")
                    val errorMessage = JSONObject(responseData).getString("errorMsg")
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
                    articleList.postValue(ArticleList(ArticleData(curPage,data,over,pageCount,size,total),errorCode,errorMessage))
                }
            })
        }
    }
}