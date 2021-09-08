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
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.activity.LoginActivity
import com.example.wanandroid.databinding.FragmentProfileBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.event.UserEvent
import com.example.wanandroid.event.refresh.QARefreshEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.UserViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ProfileFragment : BaseFragment() {
    private val application = WanAndroidApplication
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: UserViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBusUtil.register(this)
        if(application.user.id==-1){
            binding.logout.visibility = View.GONE
        }
        arguments = Bundle()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        Glide.with(this)
            .load(R.drawable.ic_logo)
            .into(binding.image)
        binding.username.setOnClickListener {
            //未登录状态,点击进行登录
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.logout.setOnClickListener {
//            viewModel
        }

//        viewModel.getUser().observe(viewLifecycleOwner, Observer {
//            binding.username.text = it.nickname
//            binding.rank.text = it.coinCount.toString()
//        }
//        )

        return binding.root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event :UserEvent){
        binding.username.text = application.user.username
        binding.rank.text = application.user.coinCount.toString()
    }

    override fun onDestroy() {
        EventBusUtil.unregister(this)
        super.onDestroy()
    }

}