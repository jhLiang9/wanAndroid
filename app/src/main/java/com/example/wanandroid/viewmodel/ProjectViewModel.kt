package com.example.wanandroid.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjectViewModel() : ViewModel(){
    var _cid = MutableLiveData<Int>(294)
    val cid :LiveData<Int>
        get() = _cid

    private var _change = MutableLiveData<Boolean>()
    val change :LiveData<Boolean> get()= _change

     fun getchange(): Boolean? {
        return _change.value
    }


    fun setChange(bool :Boolean){
        _change.value=bool
    }
}