package com.example.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.activity.baseactivity.BaseActivity
import com.example.wanandroid.adapter.CoinDetailAdapter
import com.example.wanandroid.databinding.ActivityCoinDetailBinding
import com.example.wanandroid.entity.CoinDetail
import com.example.wanandroid.entity.CoinDetailData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CoinDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityCoinDetailBinding
    private val list = ArrayList<CoinDetail>()

    companion object{
        @JvmStatic
        fun start(context: Context?){
            if(context!=null){
                val intent = Intent(context,CoinDetailActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
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