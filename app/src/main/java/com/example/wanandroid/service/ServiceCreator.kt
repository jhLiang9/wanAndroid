package com.example.wanandroid.service

import android.util.Log
import com.bumptech.glide.RequestBuilder
import com.example.wanandroid.WanAndroidApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    val httpClient:OkHttpClient.Builder =  OkHttpClient.Builder().addInterceptor(object:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val original:Request = chain.request()
            val requestBuilder = original.newBuilder()
            if(application.cookies["loginUserName"]!=""||application.cookies["loginUserName"]!=null){
                requestBuilder.addHeader("Cookie", "loginUserName="+ application.cookies["loginUserName"]+";token_pass="+ application.cookies["token_pass"])
                requestBuilder.method(original.method, original.body)
            }
            val request:Request = requestBuilder.build()
            Log.i("cookie?", request.headers.toString())
            return chain.proceed(request);

        }
    })
    private const val BASE_URL = "https://www.wanandroid.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()
    val application = WanAndroidApplication


    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}