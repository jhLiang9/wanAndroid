package com.example.wanandroid.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityShareBinding

class ShareActivity : BaseActivity() {
    private lateinit var binding: ActivityShareBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        binding = ActivityShareBinding.inflate(layoutInflater)
        binding.btCancel.setOnClickListener { finish() }
        binding.btSubmit.setOnClickListener {
            appService.shareArticle(binding.shareTitle.toString(), binding.shareLink.toString())
            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()
        }
        setContentView(binding.root)
    }
}