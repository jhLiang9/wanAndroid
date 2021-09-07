package com.example.wanandroid.activity.baseactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator

open class BaseActivity : AppCompatActivity(){

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T  = ViewModelProvider(this).get(modelClass)
    val appService = ServiceCreator.create(AppService::class.java)

}