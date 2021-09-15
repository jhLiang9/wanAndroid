package com.example.wanandroid.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.wanandroid.databinding.ActivityMyCoinBinding
import com.example.wanandroid.entity.CoinData
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyCoinBinding
    private lateinit var appService: AppService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyCoinBinding.inflate(layoutInflater)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        appService = ServiceCreator.create(AppService::class.java)
        binding.root.alpha = 0.7f
        binding.loadingPanel.visibility = View.VISIBLE
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
        setContentView(binding.root)
    }
}