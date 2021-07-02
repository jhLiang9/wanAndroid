package com.example.wanandroid.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel() : ViewModel(){
    val cid = MutableLiveData<Int>()

    init {

    }

}