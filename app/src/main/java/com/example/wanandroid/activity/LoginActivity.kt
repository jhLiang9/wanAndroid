package com.example.wanandroid.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityLoginBinding
import com.example.wanandroid.viewmodel.UserViewModel
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.activity.baseactivity.BaseActivity


class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by viewModels()

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            if (context is Activity) {
                intent.flags = FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initView()
        initViewModel()

    }

    private fun initView() {
        binding.login.setOnClickListener {
            login()
        }
        binding.register.setOnClickListener {
            RegisterActivity.start(this)
        }

    }

    private fun initViewModel() {
        viewModel.success.observe(this, {
            //登录成功 finish
            if (it) { finish() } else {
                binding.loadingPanel.visibility = View.GONE
                binding.mother.alpha = 1f
                Toast.makeText(this, "账号密码不匹配", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun login() {
        val inputMethodManager: InputMethodManager =
            applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //输入完成后隐藏输入法
        inputMethodManager.hideSoftInputFromWindow(binding.passwordEdit.windowToken, 0)
        val name = binding.accountEdit.text.toString()
        val pass = binding.passwordEdit.text.toString()
        doLogin(name, pass)
    }

    private fun doLogin(name: String, password: String) {
        viewModel.doLogin(name, password)
        binding.loadingPanel.visibility = View.VISIBLE
        binding.mother.alpha = 0.5f
    }


}