package com.example.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.Article
import com.example.wanandroid.entity.ArticleData
import com.example.wanandroid.entity.ArticleList
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class HomePageViewModel : DefaultViewModel() {

    private lateinit var set: HashSet<Int>
    private val _articleList = MutableLiveData<ArticleData?>()
    val articleList: LiveData<ArticleData?> = _articleList

    var currentPage = -1

    //下一页
    private var nextPage: Int = 1

    // 页数
    var pageCount = -1


    private fun getArticles(page: Int) {
        appService.getArticleData(page).enqueue(object : Callback<ArticleList> {
            override fun onResponse(call: Call<ArticleList>, response: Response<ArticleList>) {
                val data = response.body()?.data ?: return
                if (data.datas.isNotEmpty()) {
                    _articleList.postValue(data)
                } else {
                    _articleList.postValue(null)
                }
            }

            override fun onFailure(call: Call<ArticleList>, t: Throwable) = t.printStackTrace()
        })
    }

    fun getNextPage() = getArticles(nextPage++)

    fun getFirstPage() = getArticles(0)

    fun refresh() {
        //清除数据集，重新加载
        nextPage = 1
        getFirstPage()
    }

}