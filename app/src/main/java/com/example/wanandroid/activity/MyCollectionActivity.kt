package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.databinding.ActivityMyCollectionBinding
import com.example.wanandroid.fragment.InteractionFragment
import com.example.wanandroid.fragment.MyCollectionFragment

class MyCollectionActivity: BaseActivity() {
    private val binding by lazy{
        ActivityMyCollectionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(R.id.activity_fragment_container,MyCollectionFragment.newInstance())
        setContentView(binding.root)
    }

    fun start( context:Context){
        val intent = Intent(context,this::class.java)
        startActivity(intent)
    }
}