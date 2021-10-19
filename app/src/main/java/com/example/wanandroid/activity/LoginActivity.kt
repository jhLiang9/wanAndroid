package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityLoginBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.fragment.BlankFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.UserViewModel
import android.view.inputmethod.InputMethodManager

import android.widget.EditText


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by viewModels()

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        initView()
        initViewModel()

    }

    private fun initView() {
        binding.login.setOnClickListener {
            val inputMethodManager: InputMethodManager =
                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            //输入完成后隐藏输入法
            inputMethodManager.hideSoftInputFromWindow(binding.passwordEdit.windowToken, 0)

            val name = binding.accountEdit.text.toString()
            val pass = binding.passwordEdit.text.toString()
            login(name, pass)
            binding.loadingPanel.visibility = View.VISIBLE
            binding.mother.alpha = 0.5f

        }
        binding.register.setOnClickListener {
            RegisterActivity.start(this)
        }


    }

    private fun initViewModel() {
        viewModel.success.observe(this, {
            //登录成功 finish
            if (it) {
                finish()
            } else {
                binding.loadingPanel.visibility = View.GONE
                binding.mother.alpha = 1f
                Toast.makeText(this, "账号密码不匹配", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun login(name: String, password: String) = viewModel.login(name, password)


}