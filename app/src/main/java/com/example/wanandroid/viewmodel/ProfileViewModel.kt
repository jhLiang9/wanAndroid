package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.BaseResponse
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel:BaseViewModel() {
    val isLogin =MutableLiveData<Boolean>()
    fun logout(){

        appService.logout().enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                //set Headers
                response.headers()
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}