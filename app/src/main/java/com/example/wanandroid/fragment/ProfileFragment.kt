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
import com.bumptech.glide.Glide
import com.example.wanandroid.R
import com.example.wanandroid.activity.LoginActivity
import com.example.wanandroid.databinding.FragmentProfileBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.entity.UserData
import com.example.wanandroid.event.UserEvent
import com.example.wanandroid.event.refresh.HomepageGoUpEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.UserViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments=Bundle()
        Log.i("user","register")
        EventBusUtil.register(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        Glide.with(this)
            .load(R.drawable.ic_logo)
            .into(binding.image)
        binding.username.setOnClickListener {
            //未登录状态,点击进行登录
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        viewModel.getUser().observe(viewLifecycleOwner,{
            binding.username.text=it.nickname
            binding.rank.text=it.coinCount.toString()
        })

        return binding.root
    }

    override fun onDestroy() {
        Log.i("user","destroy")
        EventBusUtil.unregister(this)
        super.onDestroy()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UserEvent) {
        val data: UserData =event.userdata
        setUser(data.user)
        Log.i("user",event.userdata.user.toString())
    }

    fun setUser(user: User) = viewModel.setUser(user)


}