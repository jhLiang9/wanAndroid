package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityRegisterBinding
import com.example.wanandroid.viewmodel.UserViewModel

class RegisterActivity : BaseActivity() {
    private val binding by lazy {ActivityRegisterBinding.inflate(layoutInflater)}
    private val viewModel: UserViewModel by viewModels()

    companion object{
        @JvmStatic
        fun start(context:Context){
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.register.setOnClickListener {
            val username = binding.accountEdit.toString()
            val password = binding.passwordEdit.toString()
            val repassword = binding.repassword.toString()
            register(username,password,repassword)
            finish()
        }

        setContentView(binding.root)

    }

    fun register(username: String, password: String, repassword: String) =
        viewModel.register(username, password, repassword)


}
