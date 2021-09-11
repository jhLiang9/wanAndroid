package com.example.wanandroid.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.entity.User
import com.example.wanandroid.entity.data.UserData
import com.example.wanandroid.event.UserEvent
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : BaseViewModel() {
    private var user = MutableLiveData(User())
    val application = WanAndroidApplication
    val success = MutableLiveData<Boolean>()



    fun login(username: String, password: String) {

        appService.longin(username, password).enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                Log.i("user", response.toString())
                Log.i("user null", response.body()?.data.toString())
                val data = response.body()?.data
                val header = response.headers()
                for(i in header){
                    Log.i("cookie",i.first+" "+i.second)
                    if(i.first == "Set-Cookie"){
                        if(i.second.startsWith("loginUserName")){
                            var start  = -1
                            var end = -1
                            for( t in i.second.indices){
                                if(i.second[t]=='='){
                                    start = t
                                    Log.i("cookie save","start"+start)
                                }
                                else if(i.second[t]==';'){
                                    end = t
                                    Log.i("cookie save","end "+end)
                                    break
                                }

                            }
                           Log.i("cookie save",i.second.substring(start+1,end))
                            application.saveCookie("loginUserName",i.second.substring(start+1,end))
                        }else if(i.second.startsWith("token_pass")){
                            var start  = -1
                            var end = -1
                            for( t in 0.. i.second.length-1){
                                if(i.second[t]=='='){
                                    start = t
                                    Log.i("ccookie save","start"+start)
                                }
                                else if(i.second[t]==';'){
                                    end = t
                                    Log.i("ccookie save","end "+end)
                                    break
                                }

                            }
                            Log.i("cookie save",i.second.substring(start+1,end))
                            application.saveCookie("token_pass",i.second.substring(start+1,end))
                        }
                        //token_pass=5d9b90bcb70640183e09d1e755ead823; Expires=Sat, 09-Oct-2021 09:37:24 GMT;
                        //loginUserName=Hometest; Expires=Sat, 09-Oct-2021 09:37:24 GMT; Path=/
//                        application.cookie
                    }
                }
                val cookies= response.headers()["Set-Cookie"]

                if (cookies != null) {
                        Log.i("user cookie",cookies.toString())
                }
                Log.i("user",response.headers().toString())
                if (data != null) {
                    if(data.id!=-1){
                        success.postValue(true)
                        application.user = data
                        application.saveUser(data)
                        EventBusUtil.post(UserEvent())
                    }else{
                        success.postValue(false)
                    }
                }else{
                    success.postValue(false)
                }

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
                val body = response.body()
                Log.i("user", response.toString())
                Log.i("user null", response.body()?.data.toString())
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