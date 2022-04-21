package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.BaseResponse
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : DefaultViewModel() {
    // 退出登录
    val logout = MutableLiveData<Boolean>()

    /**
     * 退出登录
     */
    fun logout() {
        appService.logout().enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val headers = response.headers()
                application.clearUser()
                application.clearCookies()
                logout.postValue(true)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}