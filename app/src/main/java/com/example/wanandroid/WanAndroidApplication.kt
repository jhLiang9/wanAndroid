package com.example.wanandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.wanandroid.entity.User

class WanAndroidApplication: Application() {


    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var user:User
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //TODO 从数据库中读->更新
        user = User()
    }

}