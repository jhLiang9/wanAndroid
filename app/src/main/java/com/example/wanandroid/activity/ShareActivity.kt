package com.example.wanandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityShareBinding

class ShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareBinding.inflate(layoutInflater)
        binding.btCancel.setOnClickListener { finish() }
        binding.btSubmit.setOnClickListener {

        }
        setContentView(binding.root)
    }
}