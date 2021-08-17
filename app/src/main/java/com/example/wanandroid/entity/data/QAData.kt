package com.example.wanandroid.entity.data

import com.example.wanandroid.entity.Article

data class QAData(
    val curPage: Int,
    val datas: ArrayList<Article>,
    val over: Boolean = false,
    val pageCount: Int,
    val size: Int,
    val total: Int
)