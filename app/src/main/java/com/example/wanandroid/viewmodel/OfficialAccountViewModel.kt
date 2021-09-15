package com.example.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid.entity.WXAccountList
import com.example.wanandroid.viewmodel.baseviewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfficialAccountViewModel : BaseViewModel() {
    private var wxAccountList =MutableLiveData<WXAccountList>()
    fun getAccountList() = wxAccountList
     fun getAccounts(){
         appService.getWxAccounts().enqueue(object: Callback<WXAccountList> {
             override fun onResponse(call: Call<WXAccountList>, response: Response<WXAccountList>) {
                 val body=response.body()
                 wxAccountList.postValue(body!!)
             }

             override fun onFailure(call: Call<WXAccountList>, t: Throwable) {
                 t.printStackTrace()
             }

         })
     }


}