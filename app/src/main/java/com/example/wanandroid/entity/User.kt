package com.example.wanandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name= "name")
    val name:String,
    @ColumnInfo(name= "password")
    val password :String,
)