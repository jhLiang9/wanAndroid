package com.example.wanandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityRegisterBinding
import com.example.wanandroid.service.AppService
import com.example.wanandroid.viewmodel.UserViewModel
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.register.setOnClickListener {
            val username = binding.accountEdit.toString()
            val password = binding.passwordEdit.toString()
            val repassword = binding.repassword.toString()
            register(username,password,repassword)
            finish()
        }
    }

    fun register(username: String, password: String, repassword: String) =
        viewModel.register(username, password, repassword)
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


}
