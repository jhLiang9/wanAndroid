package com.example.wanandroid.viewmodel.baseviewmodel

import androidx.lifecycle.ViewModel
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import okhttp3.OkHttpClient

open class BaseViewModel :ViewModel(){
    //TODO 抽象
    val client = OkHttpClient()
    val appService = ServiceCreator.create(AppService::class.java)
}