package com.example.wanandroid.service

import com.example.wanandroid.entity.BaseResponse
import com.example.wanandroid.entity.data.UserData
import com.example.wanandroid.entity.list.ArticleList
import com.example.wanandroid.entity.list.TreeList
import com.example.wanandroid.entity.list.WXAccountList
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

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
     * 导航数据
     */
    @GET("navi/json")
    fun getToolNavi(): Call<ArticleList>

    /**
     * 用户登陆
     * @param username 用户名
     * @param password 用户密码
     */
    @FormUrlEncoded
    @POST("user/login/")
    fun longin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<UserData>

    @FormUrlEncoded
    @POST("user/register/")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Call<UserData>

    /**
     * 收藏文章列表
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollection(@Path("page") page: Int)

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{page}/json")
    fun postInsideCollection(@Path("page") page: Int)

    /**
     * 收藏站外文章
     */
    @FormUrlEncoded
    @POST("lg/collect/add/json/")
    fun postOutsideCollection(@Field("title") title: String,@Field("author") author: String,@Field("link") link: String)

    /**
     * 广场
     * @param page 页码【从0开始】
     * @param page_size 分页数量 【1-40】，不传使用默认值
     */
    @GET("user_article/list/{page}/json")
    fun getPlayground(@Path("page")page:Int):Call<ArticleList>

    /**
     * 登出
     */
    @GET("user/logout/json")
    fun logout():Call<BaseResponse>
    /**
     * {"data":null,"errorCode":0,"errorMsg":""}
     */

    /**
     * 微信公众号 列表
     */
    @GET("wxarticle/chapters/json")
    fun getWxAccounts():Call<WXAccountList>

    /**
     * 某个微信公众号的历史数据
     * @param id 公众号代表id
     * @param page 页码
     */
    @GET("wxarticle/list/{id}/{page}/json")
    fun getWxArticles(@Path("id")id:Int,@Path("page")page:Int):Call<ArticleList>


    /**
     * 某个微信公众号的历史数据
     * @param id 公众号代表id
     * @param page 页码
     */
    @GET("wxarticle/list/{id}/{page}/json/")
    fun getWxKeyArticles(@Path("id")id:Int,@Path("page")page:Int,@Query("k")keyword: String): Observable<ArticleList>
    //TODO 我的收藏

    /**
     *搜索
     * @param page 页码
     * @param keyword 关键字
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    fun search(@Path("page")page:Int,@Field("k")keyword:String): Call<ArticleList>

}