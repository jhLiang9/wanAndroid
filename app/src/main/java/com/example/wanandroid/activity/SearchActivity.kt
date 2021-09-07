package com.example.wanandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivitySearchBinding
import io.reactivex.rxjava3.disposables.Disposable

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        val disposable:Disposable
        setContentView(view)
    }
}