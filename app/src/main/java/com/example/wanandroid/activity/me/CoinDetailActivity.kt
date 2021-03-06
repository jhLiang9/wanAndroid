package com.example.wanandroid.activity.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.adapter.CoinDetailAdapter
import com.example.wanandroid.databinding.ActivityCoinDetailBinding
import com.example.wanandroid.entity.CoinDetail
import com.example.wanandroid.entity.CoinDetailData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class CoinDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityCoinDetailBinding
    private val list = ArrayList<CoinDetail>()

    companion object{
        @JvmStatic
        fun start(context: Context?){
            if(context!=null){
                val intent = Intent(context, CoinDetailActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_coin_detail)
        initView()
        initData()
    }

    private fun initView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(){
        appService.coinDetail(1).enqueue(object : Callback<CoinDetailData> {
            override fun onResponse(
                call: Call<CoinDetailData>,
                response: Response<CoinDetailData>
            ) {
                val body = response.body()
                if (body != null) {
                    list.addAll(body.data.datas)
                    binding.recyclerView.adapter = CoinDetailAdapter(list)
                } else {

                }
            }

            override fun onFailure(call: Call<CoinDetailData>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }


}