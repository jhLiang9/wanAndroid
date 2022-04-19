package com.example.wanandroid.activity.me

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityMyCoinBinding
import com.example.wanandroid.viewmodel.CoinViewModel

class MyCoinActivity : BaseActivity() {
    private lateinit var binding: ActivityMyCoinBinding
    private val viewModel: CoinViewModel = getViewModel(CoinViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_coin)
        initView()
        initData()
        super.onCreate(savedInstanceState)
    }

    private fun initData() = viewModel.getCoinInfo()


    private fun initView() {
        viewModel.coinData.observe(this) {
            binding.root.alpha = 0.7f
            binding.loadingPanel.visibility = View.VISIBLE

            with(it) {
                binding.coin.text = coin.coinCount.toString()
                binding.rank.text = coin.rank.toString()
                binding.userId.text = coin.userId.toString()
                binding.username.text = coin.username
                binding.root.alpha = 1f
                binding.loadingPanel.visibility = View.GONE
            }

        }
    }

}