package com.example.wanandroid.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.wanandroid.R
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.activity.LoginActivity
import com.example.wanandroid.activity.MyCollectionActivity
import com.example.wanandroid.databinding.FragmentProfileBinding
import com.example.wanandroid.entity.User
import com.example.wanandroid.event.UserEvent
import com.example.wanandroid.event.refresh.QARefreshEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.ProfileViewModel
import com.example.wanandroid.viewmodel.UserViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ProfileFragment : BaseFragment() {
    private val application = WanAndroidApplication
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        EventBusUtil.register(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        Glide.with(this)
            .load(R.drawable.ic_logo)
            .into(binding.image)
        viewModel.isLogin.observe(viewLifecycleOwner,{
            if(!it){
                binding.logout.visibility = View.GONE
                binding.username.text = application.user.username
                binding.rank.text = application.user.coinCount.toString()
                on()
            }else{
            binding.logout.visibility = View.VISIBLE
        }
        })
        if (application.user.id == -1) {
            //未登录状态,点击进行登录
            on()
            binding.logout.visibility = View.GONE

        } else {
            //已登录状态
            binding.username.text = application.user.username
            binding.rank.text = application.user.coinCount.toString()
            binding.rlCollection.setOnClickListener {
                val intent = Intent(context,MyCollectionActivity::class.java)
                startActivity(intent)
            }
        }

        binding.logout.setOnClickListener {
            val dialogFragment = LogoutDialogFragment()
            dialogFragment.show(requireActivity().supportFragmentManager, "missiles")
        }
        return binding.root
    }

    private fun on(){
        val intent = Intent(context, LoginActivity::class.java)

        binding.username.setOnClickListener {
            startActivity(intent)
        }
        binding.image.setOnClickListener {
            startActivity(intent)
        }
        binding.rlRank.setOnClickListener {
            startActivity(intent)
        }
        binding.rlCollection.setOnClickListener {
            startActivity(intent)
        }
        binding.rlProfile.setOnClickListener {
            startActivity(intent)
        }
        binding.rlNavigation.setOnClickListener {
            startActivity(intent)
        }
        binding.shareArticle.setOnClickListener {
            startActivity(intent)
        }
        binding.shareProject.setOnClickListener {
            startActivity(intent)
        }
    }

    /**
     * 登录成功后
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UserEvent) {
        binding.username.text = application.user.username
        binding.rank.text = application.user.coinCount.toString()
        binding.logout.visibility = View.VISIBLE
        binding.username.isClickable = false
    }

    override fun onDestroy() {
        EventBusUtil.unregister(this)
        super.onDestroy()
    }


}

class LogoutDialogFragment: DialogFragment(){
    private val viewModel: ProfileViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            builder.setMessage("确定退出当前账号")
                .setTitle("退出账号")
                .setPositiveButton("确定", DialogInterface.OnClickListener{
                        dialog,id->
                    viewModel.logout()
                }).setNegativeButton("取消", DialogInterface.OnClickListener{
                        _, _ ->
                })
            builder.create()

        }?:throw  IllegalStateException("activity can't be null")
    }

}