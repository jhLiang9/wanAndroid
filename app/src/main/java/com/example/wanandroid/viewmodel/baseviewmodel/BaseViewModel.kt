package com.example.wanandroid.viewmodel.baseviewmodel

import androidx.lifecycle.ViewModel
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import okhttp3.OkHttpClient

open class BaseViewModel :ViewModel(){
    protected val client = OkHttpClient()
    protected val application = WanAndroidApplication
    protected val appService = ServiceCreator.create(AppService::class.java)
}