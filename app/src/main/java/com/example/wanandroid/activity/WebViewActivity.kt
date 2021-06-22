package com.example.wanandroid.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.wanandroid.R
import kotlinx.android.synthetic.main.activity_web_view.*


class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView.settings.javaScriptEnabled=true
        webView.webViewClient = WebViewClient()
        val intent = intent

        val url: String? = intent.getStringExtra("data");
        if (url != null) {
            webView.loadUrl(url)
        }
    }
}