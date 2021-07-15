package com.example.wanandroid.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity() {
    private lateinit var binding:ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        binding = ActivityWebViewBinding.inflate(layoutInflater)

        //允许JavaScript运行

        val webView = binding.webView
        webView.settings.javaScriptEnabled=true
        webView.webViewClient = WebViewClient()
        val intent = intent
        val url: String? = intent.getStringExtra("data");
        if (url != null) {
            //加载url
            webView.loadUrl(url)
        }
    }
}