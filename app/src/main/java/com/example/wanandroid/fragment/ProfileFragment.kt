package com.example.wanandroid.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.wanandroid.R
import com.example.wanandroid.WanAndroidApplication
import com.example.wanandroid.activity.ShareActivity
import com.example.wanandroid.activity.me.CoinDetailActivity
import com.example.wanandroid.activity.me.LoginActivity
import com.example.wanandroid.activity.me.MyCoinActivity
import com.example.wanandroid.activity.me.MyCollectionActivity
import com.example.wanandroid.databinding.FragmentProfileBinding
import com.example.wanandroid.event.UserEvent
import com.example.wanandroid.fragment.basefragment.BaseFragment
import com.example.wanandroid.utils.EventBusUtil
import com.example.wanandroid.viewmodel.ProfileViewModel
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
        initView()
        initViewModel()
        return binding.root
    }

    private fun initView() {
        Glide.with(this)
            .load(R.drawable.ic_logo)
            .into(binding.image)
        binding.logout.setOnClickListener {
            val dialogFragment = LogoutDialogFragment()
            binding.parent.alpha = 0.70f
            dialogFragment.show(requireActivity().supportFragmentManager, "LogOut")
        }
    }

    private fun initViewModel() {
        viewModel.logout.observe(viewLifecycleOwner) {
            binding.parent.alpha = 1f
            if (!it) {
                binding.logout.visibility = View.GONE
                binding.username.text = application.user.username
                binding.rank.text = application.user.coinCount.toString()
                updateView(false)
            } else {
                updateView(true)
                binding.logout.visibility = View.VISIBLE
            }
        }
        if (application.user.id == -1) {
            //未登录状态,点击进行登录
            updateView(false)
            binding.logout.visibility = View.GONE

        } else {
            //已登录状态
            updateView(true)
        }
    }

    private fun updateView(auth: Boolean) {
        if (auth) {
            binding.username.text = application.user.username
            binding.rank.text = application.user.coinCount.toString()
            binding.rlCollection.setOnClickListener {
                val intent = Intent(context, MyCollectionActivity::class.java)
                startActivity(intent)
            }
            binding.shareArticle.setOnClickListener {
                ShareActivity.start(context)
            }
            binding.shareProject.setOnClickListener {
                ShareActivity.start(context)
            }
            binding.rlRank.setOnClickListener {
                val intent = Intent(context, CoinDetailActivity::class.java)
                startActivity(intent)
            }
            binding.rlProfile.setOnClickListener {
                val intent = Intent(context, MyCoinActivity::class.java)
                startActivity(intent)
            }
        } else {
            binding.username.setOnClickListener {
                LoginActivity.start(context)
            }
            binding.image.setOnClickListener {
                LoginActivity.start(context)
            }
            binding.rlRank.setOnClickListener {
                LoginActivity.start(context)
            }
            binding.rlCollection.setOnClickListener {
                LoginActivity.start(context)
            }
            binding.rlProfile.setOnClickListener {
                LoginActivity.start(context)
            }
            binding.rlNavigation.setOnClickListener {
                LoginActivity.start(context)
            }
            binding.shareArticle.setOnClickListener {
                LoginActivity.start(context)
            }
            binding.shareProject.setOnClickListener {
                LoginActivity.start(context)
            }
        }

    }


    /**
     * 登录成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: UserEvent) {
        binding.username.text = application.user.username
        binding.rank.text = application.user.coinCount.toString()
        binding.logout.visibility = View.VISIBLE
        binding.username.isClickable = false
        updateView(true)
    }

    override fun onDestroy() {
        EventBusUtil.unregister(this)
        super.onDestroy()
    }


}

class LogoutDialogFragment : DialogFragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("确定退出当前账号")
                .setTitle("退出账号")
                .setPositiveButton("确定") { _, _ ->
                    viewModel.logout()
                }.setNegativeButton("取消") { _, _ ->
                    dismiss()
                }
            builder.create()

        } ?: throw  IllegalStateException("activity can't be null")
    }

}