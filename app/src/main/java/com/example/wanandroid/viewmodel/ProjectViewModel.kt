package com.example.wanandroid.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjectViewModel() : ViewModel(){
    private val _cid = MutableLiveData<Int>(294)
    val cid :LiveData<Int>
        get() = _cid

}