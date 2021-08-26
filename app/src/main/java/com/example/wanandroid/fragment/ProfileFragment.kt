package com.example.wanandroid.fragment

import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.activity.LoginActivity
import com.example.wanandroid.databinding.FragmentHomePageBinding
import com.example.wanandroid.databinding.FragmentProfileBinding
import com.example.wanandroid.fragment.basefragment.BaseFragment


class ProfileFragment : BaseFragment() {

    private val user:String?=null
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        binding.username.setOnClickListener {
            if(user==null){//未登录状态,点击进行登录
                val intent:Intent=Intent(activity,LoginActivity::class.java)
                startActivityForResult(intent,1)//返回用户,用intent承载
                binding.username.setText(intent.getStringExtra("name"))

                val collect =intent.getStringExtra("collectIds")
                TODO("获得用户收藏")

            }
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


}