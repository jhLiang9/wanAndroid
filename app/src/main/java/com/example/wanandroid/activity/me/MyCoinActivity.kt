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
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_coin)
        viewModel = getViewModel(CoinViewModel::class.java)
        initView()
        initData()
        super.onCreate(savedInstanceState)
    }

    private fun initData() = viewModel.getCoinInfo()

    private fun initView() {
        viewModel.coinData.observe(this) {
            binding.root.alpha = 0.7f
            binding.loadingPanel.visibility = View.VISIBLE
            it?.let {
                with(binding) {
                    coin.text = it.coin.coinCount.toString()
                    rank.text = it.coin.rank.toString()
                    userId.text = it.coin.userId.toString()
                    username.text = it.coin.username
                    root.alpha = 1f
                    loadingPanel.visibility = View.GONE
                }
            }
        }
    }

}