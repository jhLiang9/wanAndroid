package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityShareBinding

class ShareActivity : BaseActivity() {
    private lateinit var binding: ActivityShareBinding

    companion object {
        @JvmStatic
        fun start(context: Context?) {
            val intent = Intent(context, ShareActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share)
        initView()
    }

    private fun initView() {
        binding.btCancel.setOnClickListener { finish() }
        binding.btSubmit.setOnClickListener {
            appService.shareArticle(binding.shareTitle.toString(), binding.shareLink.toString())
            Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show()
        }

    }


}