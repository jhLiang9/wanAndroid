package com.example.wanandroid.entity.list

import com.example.wanandroid.entity.WXAccount

data class WXAccountList(
    val data: ArrayList<WXAccount>? = null,
    val errorCode: Int = -1,
    val errorMsg: String = ""
)