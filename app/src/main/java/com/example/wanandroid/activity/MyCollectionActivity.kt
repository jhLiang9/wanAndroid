package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityMainBinding
import com.example.wanandroid.databinding.ActivityMyCollectionBinding
import com.example.wanandroid.fragment.InteractionFragment
import com.example.wanandroid.fragment.MyCollectionFragment

class MyCollectionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMyCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCollectionBinding.inflate(layoutInflater)
        val view = binding.root
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.activity_fragment_container,
            MyCollectionFragment.newInstance()
        ).commit()
        setContentView(binding.root)
    }

    fun start( context:Context){
        val intent = Intent(context,this::class.java)
        startActivity(intent)
    }
}