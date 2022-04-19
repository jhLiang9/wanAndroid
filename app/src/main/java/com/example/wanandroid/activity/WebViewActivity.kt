package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityWebViewBinding


class WebViewActivity : BaseActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private var url: String? = ""


    companion object {
        fun start(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)

        initWebView()
        parseIntent()

        url?.let { binding.webView.loadUrl(it) }

    }

    private fun parseIntent() {
        url = intent.getStringExtra("url")
    }

    private fun initWebView() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        //允许JavaScript运行
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                cookieManager.setCookie(url, cookieManager.getCookie(url))
                Log.i("cookieManager set", cookieManager.getCookie(url))
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                val cookie = cookieManager.getCookie(url)
                Log.i("cookieManager", cookie)
                super.onPageFinished(view, url)
            }
        }
        binding.webView.settings.javaScriptEnabled = true
    }
}