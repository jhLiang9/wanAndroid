package com.example.wanandroid.entity.data

import com.example.wanandroid.entity.Article

data class ArticleData(
    val curPage: Int,
    val datas: List<Article>,
    val over: Boolean = false,
    val pageCount: Int,
    val size: Int,
    val total: Int
) {

}