package com.example.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.User
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel

class UserViewModel : BaseViewModel() {
    private lateinit var user: MutableLiveData<User>


    fun setUser(u: User) = user.postValue(u)


    fun getUser(): MutableLiveData<User> {
        return if(this::user.isInitialized){
            user
        }else{
            MutableLiveData(User(coinCount = -1,id=-1,chapterTops = ArrayList(),nickname="None",type=-1,collectIds = ArrayList()))
        }
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