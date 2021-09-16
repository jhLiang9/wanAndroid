package com.example.wanandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wanandroid.entity.converter.UserConverter
import java.io.Serializable

/**
 * 用户信息
 */
@Entity(tableName = "user_table")
@TypeConverters(UserConverter::class)
data class User (
    var admin :Boolean= false,
    var chapterTops:ArrayList<Any>?=null,
    var coinCount:Int =-1,
    var collectIds:ArrayList<Any>?=null,
    var email:String="",
    var icon :String="",
    @PrimaryKey(autoGenerate = false)
    var id:Int=-1,
    var nickname:String="未登录",
    var password :String="",
    var publicName:String="",
    var token:String="",
    var type:Int=-1,
    var username:String = "未登录"
):Serializable

data class UserData(val data: User?=null):BaseResponse()