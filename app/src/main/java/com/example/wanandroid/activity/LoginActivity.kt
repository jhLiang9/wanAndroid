package com.example.wanandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityLoginBinding
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity()  {
    private lateinit var binding :ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
        setSupportActionBar(binding.logintoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用


        /**
         * {
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
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logintoolbar,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.more -> Toast.makeText(this, " HELP", Toast.LENGTH_SHORT).show()
            R.id.register ->{
                val intent=Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun login(username:String,password:String){
        val appService = ServiceCreator.create(AppService::class.java)

//        appService.longin(username,password).enqueue(object : Callback<ArticleList> {
//            override fun onResponse(
//                call: Call<ArticleList>,
//                response: Response<ArticleList>
//            ) {
//                val body = response.body()
//                .postValue(body)
//            }
//
//            override fun onFailure(call: Call<ArticleList>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
    }

}