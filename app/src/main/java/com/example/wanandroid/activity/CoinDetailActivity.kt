package com.example.wanandroid.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityCoinDetailBinding
import com.example.wanandroid.entity.CoinDetail
import com.example.wanandroid.entity.CoinDetailData
import com.example.wanandroid.service.AppService
import com.example.wanandroid.service.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CoinDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoinDetailBinding
    private lateinit var appService: AppService
    private val list = ArrayList<CoinDetail>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        appService = ServiceCreator.create(AppService::class.java)

        appService.coinDetail(1).enqueue(object : Callback<CoinDetailData> {
            override fun onResponse(
                call: Call<CoinDetailData>,
                response: Response<CoinDetailData>
            ) {
                val body = response.body()
                if (body != null) {
                    list.addAll(body.data.datas)
                    binding.recyclerView.adapter = CoinDetailAdapter(list)
                }else{

                }
            }

            override fun onFailure(call: Call<CoinDetailData>, t: Throwable) {
                t.printStackTrace()
            }
        })


        setContentView(binding.root)
    }

    class CoinDetailAdapter(val list: ArrayList<CoinDetail>) :
        RecyclerView.Adapter<CoinDetailAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val reason: TextView = view.findViewById(R.id.reason)
            val amount: TextView = view.findViewById(R.id.amount)
            val date: TextView = view.findViewById(R.id.date)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CoinDetailAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_coin_detail, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: CoinDetailAdapter.ViewHolder, position: Int) {

            val dNow = Date()
            val ft = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            ft.format(dNow)
            val time = list[position].date
            val res= Date(time)
            holder.date.text = ft.format(res)

            var start = -1
            val length = list[position].desc.length
            for(i in 0.. length-1){
                if ( list[position].desc[i]=='+'){
                    start = i
                }
            }
            val amount = list[position].desc.substring(start,length)
            holder.amount.text = amount
            holder.reason.text = list[position].reason

        }

        override fun getItemCount(): Int = list.size

    }
}