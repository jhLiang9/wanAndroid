package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.entity.BaseResponse
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import okhttp3.Cookie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel:BaseViewModel() {
    val application = WanAndroidApplication
    val isLogin =MutableLiveData<Boolean>()
    fun logout(){
        appService.logout().enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val headers = response.headers()
                application.clearUser()
                application.clearCookies()
                isLogin.postValue(false)

            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}