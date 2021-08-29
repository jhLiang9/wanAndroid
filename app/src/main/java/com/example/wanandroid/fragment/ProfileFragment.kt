package com.example.wanandroid.fragment

import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid.R
import com.example.wanandroid.activity.LoginActivity
import com.example.wanandroid.databinding.FragmentProfileBinding
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.viewmodel.UserViewModel


class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        Log.i("Profile", "1234")
        binding.name.setOnClickListener {
            //未登录状态,点击进行登录
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)//返回用户,用intent承载
        }

        viewModel.getUser().observe(viewLifecycleOwner,{
            binding.username.text=it.nickname
            binding.rank.text=it.coinCount.toString()
        })

        return binding.root
    }


}