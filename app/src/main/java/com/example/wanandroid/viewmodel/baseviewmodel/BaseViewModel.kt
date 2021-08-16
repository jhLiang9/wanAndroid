package com.example.wanandroid.viewmodel.baseviewmodel

import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient

open class BaseViewModel :ViewModel(){
    //TODO 抽象
    val client = OkHttpClient()
}