package com.example.wanandroid.viewmodel.defaultviewmodel

import androidx.lifecycle.ViewModel
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import okhttp3.OkHttpClient

open class DefaultViewModel : ViewModel() {
    protected val client = OkHttpClient()
    protected val application = WanAndroidApplication
    protected val appService = ServiceCreator.create(AppService::class.java)
}