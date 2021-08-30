package com.example.wanandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wanandroid.entity.converter.UserConverter

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
data class User(
    val admin :Boolean= false,
    val chapterTops:ArrayList<Any>,
    val coinCount:Int ,
    val collectIds:ArrayList<Any>,
    val email:String="",
    val icon :String="",
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    @ColumnInfo(name= "name")
    val nickname:String,
    @ColumnInfo(name= "password")
    val password :String="",
    val publicName:String="",
    val token:String="",
    val type:Int,
    val username:String = ""
)

data class UserData(val user:User,val errorCode:Int=-1,val errorMessage:String="")