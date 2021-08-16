package com.example.wanandroid.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjectViewModel() : ViewModel(){
    var _cid = MutableLiveData<Int>(294)
    val cid :LiveData<Int>
        get() = _cid

    var change = MutableLiveData<Boolean>()

     fun getChange(): Boolean? {
        return change.value
    }

    fun setChange(bool :Boolean){
        change.postValue(bool)
    }
}