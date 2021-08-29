package com.example.wanandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.wanandroid.R
import com.example.wanandroid.service.AppService
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
    }

    /**
     * {
    "data": {
    "admin": false,
    "chapterTops": [],
    "coinCount": 0,
    "collectIds": [],
    "email": "",
    "icon": "",
    "id": 109037,
    "nickname": "Hometest0",
    "password": "",
    "publicName": "Hometest0",
    "token": "",
    "type": 0,
    "username": "Hometest0"
    },
    "errorCode": 0,
    "errorMsg": ""
    }
     */

    private fun register(username:String,password:String,repassword:String){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)
//        appService.longin(username,password,repassword).enqueue
    }

}
