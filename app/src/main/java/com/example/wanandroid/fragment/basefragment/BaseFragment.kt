package com.example.wanandroid.fragment.basefragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator

open class BaseFragment: Fragment() {
    val appService = ServiceCreator.create(AppService::class.java)
    fun <T: ViewModel> getViewModel(modelClass: Class<T>):T = ViewModelProvider(this).get(modelClass)

}