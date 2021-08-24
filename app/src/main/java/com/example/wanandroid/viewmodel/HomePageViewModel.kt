package com.example.wanandroid.viewmodel

import android.util.Log
import com.example.wanandroid.utils.HtmlElementUtil
//import okhttp3.*
import java.io.IOException
import com.google.gson.Gson
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.service.AppService
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class HomePageViewModel : BaseViewModel() {

    private lateinit var set: HashSet<Int>
    val articleList = MutableLiveData<ArticleList>()
    var presentList = ArrayList<Article>()

    //下一页
    var nextPage: Int = 1
    var pageCount: Int = -1
    fun refresh() {
        nextPage = 1
        getArticlesByPage(0)
    }

    //TODO 具体是怎样的
    fun getArticlesByPage(page: Int) {
        getByRetrofit(page)
    }
//
//    suspend fun get(page: Int){
//        val url = "https://www.wanandroid.com/article/list/${page}/json"
//        val request = Request.Builder()
//            .url(url)
//            .build()
//        val call: Call = client.newCall(request)
//
//        return withContext(Dispatchers.IO){
//            call.enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    e.printStackTrace()
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    val gson = Gson()
//                    val responseData = response.body?.string()
//                    val data = gson.fromJson(responseData, ArticleList::class.java)
//                    pageCount = data.data.pageCount
//                    articleList.postValue(data)
//                }
//            })
//        }
//    }

    /**
     * @param page 页码
     */
    private fun getByRetrofit(page: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
        appService.getArticleData(page).enqueue(object : Callback<ArticleList> {
            override fun onResponse(
                call: Call<ArticleList>,
                response: Response<ArticleList>
            ) {
                val body = response.body()
                articleList.postValue(body)
            }

            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}