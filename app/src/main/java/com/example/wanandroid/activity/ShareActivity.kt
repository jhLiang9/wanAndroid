package com.example.wanandroid.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityShareBinding
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator

class ShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareBinding
    private lateinit var appService:AppService
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)


        appService= ServiceCreator.create(AppService::class.java)
        binding = ActivityShareBinding.inflate(layoutInflater)
        binding.btCancel.setOnClickListener { finish() }
        binding.btSubmit.setOnClickListener {
            appService.shareArticle(binding.shareTitle.toString(),binding.shareLink.toString())
            Toast.makeText(this,"分享成功",Toast.LENGTH_SHORT).show()
        }
        setContentView(binding.root)
    }
}