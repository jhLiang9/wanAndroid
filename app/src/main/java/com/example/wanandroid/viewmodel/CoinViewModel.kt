package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.CoinData
import com.example.wanandroid.viewmodel.defaultviewmodel.DefaultViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinViewModel : DefaultViewModel() {
    val coinData = MutableLiveData<CoinData>()

    fun getCoinInfo() {
        appService.coinData().enqueue(object : Callback<CoinData> {
            override fun onResponse(call: Call<CoinData>, response: Response<CoinData>) {
                val body = response.body() ?: return
                coinData.postValue(body)
            }
            override fun onFailure(call: Call<CoinData>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

}