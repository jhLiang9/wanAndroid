package com.example.wanandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.databinding.ActivityLoginBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.entity.UserData
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.event.UserEvent
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.UserViewModel

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)


//        supportActionBar?.setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
//        supportActionBar?.setHomeButtonEnabled(true); //设置返回键可用

        binding.login.setOnClickListener {
            val name = binding.accountEdit.text.toString()
            val pass = binding.passwordEdit.text.toString()
            Log.i("user", name.toString())
            Log.i("user", pass.toString())
            login(name, pass)
        }
        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }

    private fun login(username: String, password: String) {
        val appService = ServiceCreator.create(AppService::class.java)

        appService.longin(username, password).enqueue(object : Callback<UserData> {
            override fun onResponse(
                call: Call<UserData>,
                response: Response<UserData>
            ) {
                val body = response.body()
                Thread.sleep(2000L)
                Log.i("user", body.toString())
                EventBusUtil.post(UserEvent(body!!))
                finish()
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}