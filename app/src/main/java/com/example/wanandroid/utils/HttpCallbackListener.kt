package com.example.wanandroid.utils

interface HttpCallbackListener {
    fun onFinish(response:String)
    fun onError(e:Exception)
}