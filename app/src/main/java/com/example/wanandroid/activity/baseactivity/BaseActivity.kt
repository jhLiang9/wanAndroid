package com.example.wanandroid.activity.baseactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseActivity : AppCompatActivity(){

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T  = ViewModelProvider(this).get(modelClass)


}