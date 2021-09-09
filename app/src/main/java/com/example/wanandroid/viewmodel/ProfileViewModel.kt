package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel

class ProfileViewModel:BaseViewModel() {
    val isLogin =MutableLiveData<Boolean>()

}