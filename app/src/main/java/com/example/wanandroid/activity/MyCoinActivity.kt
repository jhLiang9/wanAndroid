package com.example.wanandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wanandroid.databinding.ActivityMyCoinBinding
import com.example.wanandroid.databinding.ActivityShareBinding
import com.example.wanandroid.service.AppService

class MyCoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyCoinBinding
    private lateinit var appService: AppService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCoinBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}