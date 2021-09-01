package com.example.wanandroid.fragment

import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wanandroid.R
import com.example.wanandroid.activity.LoginActivity
import com.example.wanandroid.databinding.FragmentProfileBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.event.UserEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.UserViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments = Bundle()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        Glide.with(this)
            .load(R.drawable.ic_logo)
            .into(binding.image)
        binding.username.setOnClickListener {
            //未登录状态,点击进行登录
            val intent = Intent(context, LoginActivity::class.java)
            startActivityForResult(intent,1)
        }
        viewModel.getUser().observe(viewLifecycleOwner, Observer {
            binding.username.text = it.nickname
            binding.rank.text = it.coinCount.toString()
        }
        )

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode ==1){
            val user=data?.getBundleExtra("data")
            val value=user?.getSerializable("user")
            val  a=value as User
            Log.i("user",a.toString())
            viewModel.getUser().postValue(a)
            Log.i("user","post")
        }
        if(requestCode ==1){
            val user=data?.getBundleExtra("data")
            val value=user?.getSerializable("user")
            val  a=value as User
            Log.i("user",a.toString())
            viewModel.getUser().postValue(a)
            Log.i("user","post")
        }
    }


}