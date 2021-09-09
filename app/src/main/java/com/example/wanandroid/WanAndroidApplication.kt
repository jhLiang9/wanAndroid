package com.example.wanandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.wanandroid.entity.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WanAndroidApplication : Application() {


    companion object {


        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var user: User
        lateinit var cookies:ArrayList<HashMap<String,String>>
        var login :Boolean = false
        fun clearUser() {
            val prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit()
            if (prefs != null) {
                prefs.remove("username")
                prefs.remove("admin")
                prefs.remove("chapterTops")
                prefs.remove("coinCount")
                prefs.remove("collectIds")
                prefs.remove("email")
                prefs.remove("icon")
                prefs.remove("id")
                prefs.remove("nickname")
                prefs.remove("password")
                prefs.remove("publicName")
                prefs.remove("token")
                prefs.remove("type")
                prefs.apply()
            }
        }
        fun saveUser(user:User){
            val prefs = context.getSharedPreferences("user", Context.MODE_PRIVATE)?.edit()
            if (prefs != null) {
                val gson = Gson()
                val chapterTops= gson.toJson(user.chapterTops)
                val collectIds= gson.toJson(user.collectIds)
                prefs.putString("username",user.username)
                prefs.putBoolean("admin",user.admin)
                prefs.putString("chapterTops",chapterTops)
                prefs.putInt("coinCount",user.coinCount)
                prefs.putString("collectIds",collectIds)
                prefs.putString("email",user.email)
                prefs.putString("icon",user.icon)
                prefs.putInt("id",user.id)
                prefs.putString("nickname",user.nickname)
                prefs.putString("password",user.password)
                prefs.putString("publicName",user.publicName)
                prefs.putString("token",user.token)
                prefs.putInt("type",user.type)
                prefs.apply()
            }
        }

        fun saveCookie(key:String,value:String){
            val prefs = context.getSharedPreferences("cookies", Context.MODE_PRIVATE)?.edit()
            if (prefs != null) {
                prefs.putString(key,value)
                prefs.apply()

            }
            val map =HashMap<String,String>()
            map.put(key,value)
            cookies.add(map)
        }

        fun clearCookies(){
            cookies.clear()
            val prefs = context.getSharedPreferences("cookies", Context.MODE_PRIVATE)?.edit()
            if (prefs != null) {
                prefs.clear()
                prefs.apply()
            }

        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //TODO 从数据库中读->更新
        user = initUser()
        cookies = initCookie()
        login = initLogin()
    }

    private fun initLogin(): Boolean {
        val prefs = getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = prefs.getInt("id",-1)
        return id != -1
    }

    private fun initCookie(): ArrayList<Pair<String,String>> {
        val


    }

    private fun initUser(): User {
        val gson = Gson()
        val prefs = getSharedPreferences("user", Context.MODE_PRIVATE)
        val name = prefs.getString("username", "未登录")
        val coinCount = prefs.getInt("coinCount", -1)
        val id = prefs.getInt("id", -1)
        val admin = prefs.getBoolean("admin", false)
        val _chapterTops = prefs.getString("chapterTops", "")
        val _collectIds = prefs.getString("collectIds", "")

        val chapterTops: ArrayList<Any> = if (_chapterTops == "") {
            ArrayList()
        } else {
            gson.fromJson(_chapterTops, object : TypeToken<ArrayList<Any?>?>() {}.type)
        }

        val collectIds: ArrayList<Any> = if (_collectIds == "") {
            ArrayList()
        } else {
            gson.fromJson(_collectIds, object : TypeToken<ArrayList<Any?>?>() {}.type)
        }


        val email = prefs.getString("email", "")
        val icon = prefs.getString("icon", "")
        val password = prefs.getString("password", "")
        val publicName = prefs.getString("publicName", "")
        val token = prefs.getString("token", "")
        val nickname = prefs.getString("nickname", "")
        val type = prefs.getInt("type", -1)
        return User(
            admin,
            chapterTops,
            coinCount,
            collectIds,
            email!!,
            icon!!,
            id,
            nickname!!,
            password!!,
            publicName!!,
            token!!,
            type,
            name!!
        )
    }



}