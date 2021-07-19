package com.example.wanandroid.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class System(
    @ColumnInfo(name = "children")
    val children: List<System>?,
    @ColumnInfo(name = "courseId")
    val courseId:Int,
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "order")
    val order:Int,
    @ColumnInfo(name = "parentChapterID")
    val parentChapterID:Int,
    @ColumnInfo(name = "userControlSetTop")
    val userControlSetTop:Boolean,
    @ColumnInfo(name = "visible")
    val visible:Int) {

}