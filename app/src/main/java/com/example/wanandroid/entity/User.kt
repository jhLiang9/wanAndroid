package com.example.wanandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name= "name")
    val name:String,
    @ColumnInfo(name= "password")
    val password :String
)