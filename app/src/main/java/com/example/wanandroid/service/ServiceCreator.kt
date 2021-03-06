package com.example.wanandroid.service

import com.example.wanandroid.WanAndroidApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "https://www.wanandroid.com/"
    private val application = WanAndroidApplication

    private val httpClient: OkHttpClient.Builder =
        OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder = original.newBuilder()
            if (application.cookies["loginUserName"] != "" || application.cookies["loginUserName"] != null) {
                requestBuilder.addHeader(
                    "Cookie",
                    "loginUserName=" + application.cookies["loginUserName"] + ";token_pass=" + application.cookies["token_pass"]
                )
                requestBuilder.method(original.method, original.body)
            }
            val request: Request = requestBuilder.build()
            chain.proceed(request);
        })

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .build()


    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}