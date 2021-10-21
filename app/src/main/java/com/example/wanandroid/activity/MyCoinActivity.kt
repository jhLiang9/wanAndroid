package com.example.wanandroid.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.databinding.ActivityMyCoinBinding
import com.example.wanandroid.entity.CoinData
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCoinActivity : BaseActivity() {
    private lateinit var binding: ActivityMyCoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_coin)
        binding.root.alpha = 0.7f
        binding.loadingPanel.visibility = View.VISIBLE

        initData()
    }

    private fun initData(){
        appService.coinData().enqueue(object : Callback<CoinData> {
            override fun onResponse(call: Call<CoinData>, response: Response<CoinData>) {
                val body = response.body()
                if (body != null) {
                    binding.coin.text = body.data.coinCount.toString()
                    binding.rank.text = body.data.rank.toString()
                    binding.userId.text = body.data.userId.toString()
                    binding.username.text = body.data.username
                    binding.root.alpha = 1f
                    binding.loadingPanel.visibility = View.GONE

                }
            }

            override fun onFailure(call: Call<CoinData>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}