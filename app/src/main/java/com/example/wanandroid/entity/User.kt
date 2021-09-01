package com.example.wanandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wanandroid.entity.converter.UserConverter
import java.io.Serializable

/**
 * {
"data": {
"admin": false,
"chapterTops": [],
"coinCount": 21,
"collectIds": [],
"email": "",
"icon": "",
"id": 108609,
"nickname": "Hometest",
"password": "",
"publicName": "Hometest",
"token": "",
"type": 0,
"username": "Hometest"
},
"errorCode": 0,
"errorMsg": ""
}
 */
@Entity(tableName = "user_table")
@TypeConverters(UserConverter::class)
data class User (
    val admin :Boolean= false,
    val chapterTops:ArrayList<Any>?=null,
    val coinCount:Int =-1,
    val collectIds:ArrayList<Any>?=null,
    val email:String="",
    val icon :String="",
    @PrimaryKey(autoGenerate = false)
    val id:Int=-1,
    val nickname:String="未登录",
    val password :String="",
    val publicName:String="",
    val token:String="",
    val type:Int=-1,
    val username:String = "未登录"
):Serializable

