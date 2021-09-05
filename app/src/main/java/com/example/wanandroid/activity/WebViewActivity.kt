package com.example.wanandroid.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
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
        window.statusBarColor= Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //允许JavaScript运行
        val webView = binding.webView
//        val webView:WebView =findViewById(R.id.webView)
        webView.settings.javaScriptEnabled=true
        webView.webViewClient = WebViewClient()
        val intent = intent
        val url: String? = intent.getStringExtra("data");
        if (url != null) {
            webView.loadUrl(url)
        }
        setContentView(binding.root)
    }
}