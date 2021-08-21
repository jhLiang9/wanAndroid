package com.example.wanandroid.viewmodel

import android.util.Log
import com.example.wanandroid.utils.HtmlElementUtil
import okhttp3.*
import java.io.IOException
import com.google.gson.Gson
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class HomePageViewModel : BaseViewModel() {

    private lateinit var set: HashSet<Int>
    val articleList = MutableLiveData<ArticleList>()
    val presentList = ArrayList<Article>()
    //下一页
    var nextPage: Int = 1
    var pageCount :Int = -1
    fun refresh() {
        nextPage = 1
        getArticlesByPage(0)
    }

    //TODO 具体是怎样的
    fun getArticlesByPage(page: Int) {
        viewModelScope.launch {
            get(page)
        }
    }

    suspend fun get(page: Int){
        val url = "https://www.wanandroid.com/article/list/${page}/json"
        val request = Request.Builder()
            .url(url)
            .build()
        val call: Call = client.newCall(request)

        return withContext(Dispatchers.IO){
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val responseData = response.body?.string()
                    val data = gson.fromJson(responseData, ArticleList::class.java)
                    pageCount = data.data.pageCount
                    articleList.postValue(data)
                }
            })
        }
    }
}