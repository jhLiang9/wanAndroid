package com.example.wanandroid.service

import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.entity.list.TreeList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 网络请求
 * 获取数据、登陆、登出、注册
 */
interface AppService {
    /**
     * 首页文章
     * @param page 页码
     */
    @GET("article/list/{page}/json")
    fun getArticleData(@Path("page") page: Int): Call<ArticleList>

    /**
     * 问答
     * @param page 页码
     */
    @GET("wenda/list/{page}/json")
    fun getWenDa(@Path("page") page: Int): Call<ArticleList>

    /**
     * 知识体系树
     */
    @GET("tree/json")
    fun getSystemTree(): Call<TreeList>

    /**
     * 导航
     */
    @GET("navi/json")
    fun getNavi():Call<ArticleList>


    /**
     * 用户登陆
     * @param username 用户名
     * @param password 用户密码
     */
    @POST("user/login/")
    fun longin(@Query("username") username: String, @Query("password") password: String) :Call<Any>

    @POST("user/register/")
    fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") repassword: String
    )

    @GET("user/logout/json")
    fun logout()

    @GET("wxarticle/chapters/json")
    fun getWXArticles()

    //TODO 我的收藏、搜索、公众号
}