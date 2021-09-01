package com.example.wanandroid.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.User
import com.example.wanandroid.entity.data.UserData
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UserViewModel : BaseViewModel() {
    private var user = MutableLiveData(User())

    fun getUser(): MutableLiveData<User> {
        return user
    }

    fun login(username: String, password: String) {

        appService.longin(username, password).enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                Thread.sleep(6000L)
                Log.i("user", response.toString())
                Log.i("user null", response.body()?.data.toString())
                user.postValue(response.body()?.data)
                Log.i("user", "posted")
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun register(username: String, password: String, repassword: String) {
        appService.register(username, password, repassword).enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                Thread.sleep(4000L)
                Log.i("user", response.toString())
                Log.i("user null", response.body()?.data.toString())
                user.postValue(response.body()?.data)
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}

/**
 *
{
"data": {
"admin": false,
"chapterTops": [],
"coinCount": 21,
"collectIds": [],
"email": "",
"icon": "",
"id": 108609,
"nickname": "Hometest",
"password": "",
"publicName": "Hometest",
"token": "",
"type": 0,
"username": "Hometest"
},
"errorCode": 0,
"errorMsg": ""
}
 */

/**
 * logout
 * {
"data": null,
"errorCode": 0,
"errorMsg": ""
}
 */