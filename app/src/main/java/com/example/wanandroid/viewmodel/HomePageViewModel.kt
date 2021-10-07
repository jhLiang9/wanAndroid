package com.example.wanandroid.viewmodel

import com.example.wanandroid.utils.HtmlElementUtil
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class HomePageViewModel : BaseViewModel() {

    private lateinit var set: HashSet<Int>
    val articleList = MutableLiveData<ArticleList>()
    var presentList = ArrayList<Article>()
    var currentPage = -1

    //下一页
    var nextPage: Int = 1
    var pageCount: Int = -1




    /**
     * @param page 页码
     */
    private fun getArticles(page: Int) {
        appService.getArticleData(page).enqueue(object : Callback<ArticleList> {
            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) =
                articleList.postValue(response.body()!!)

            override fun onFailure(call: Call<ArticleList>, t: Throwable) = t.printStackTrace()
        })
    }

    fun getNextPage() = getArticles(nextPage++)

    fun getFirstPage() = getArticles(0)

    fun refresh() {
        //清除数据集，重新加载
        presentList.clear()
        nextPage = 1
        getFirstPage()
    }

}