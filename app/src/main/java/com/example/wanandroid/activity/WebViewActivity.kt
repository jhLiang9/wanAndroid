package com.example.wanandroid.activity

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
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
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        //允许JavaScript运行
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        val webView = binding.webView
        webView.webViewClient=object:WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                cookieManager.setCookie(url,cookieManager.getCookie(url))
                Log.i("cookieManager set",cookieManager.getCookie(url))
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                val cookie = cookieManager.getCookie(url)
                Log.i("cookieManager",cookie)
                super.onPageFinished(view, url)
            }

        }
        webView.settings.javaScriptEnabled=true

        val intent = intent

        val url: String? = intent.getStringExtra("data");
        if (url != null) {
            webView.loadUrl(url)
        }
        setContentView(binding.root)
    }
}