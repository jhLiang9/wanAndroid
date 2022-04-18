package com.example.wanandroid.entity

import androidx.room.*
import com.example.wanandroid.entity.converter.UserConverter

/**
 * 用户信息
 */
@Entity(tableName = "user_table")
@TypeConverters(UserConverter::class)
data class User(
    @ColumnInfo(name = "admin")
    var admin: Boolean,
    var chapterTops: ArrayList<Any>?,
    var coinCount: Int = -1,
    var collectIds: ArrayList<Any>?,
    var email: String,
    var icon: String,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var nickname: String,
    var password: String,
    var publicName: String,
    var token: String,
    var type: Int,
    var username: String
)

data class UserData(val data: User? = null) : BaseResponse()