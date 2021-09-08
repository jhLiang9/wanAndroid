package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.databinding.ActivityLoginBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.UserViewModel


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.login.setOnClickListener {
            val name = binding.accountEdit.text.toString()
            val pass = binding.passwordEdit.text.toString()
            login(name, pass)

            val intent = Intent()
            val bundle=Bundle()
//            bundle.putSerializable("user",viewModel.getUser().value)
            intent.putExtra("data",bundle)
            finish()
        }
        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun login(name:String,password:String) = viewModel.login(name,password)

    fun start(context:Context){
        val intent = Intent(context,LoginActivity::class.java)
        context.startActivity(intent)
    }
}