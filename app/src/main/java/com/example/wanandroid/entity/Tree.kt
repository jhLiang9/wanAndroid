package com.example.wanandroid.entity

import androidx.room.*
import com.example.wanandroid.entity.converter.TreeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "tree_table")

data class Tree(
    @ColumnInfo(name = "children")
    val children: ArrayList<Tree>,

    @ColumnInfo(name = "courseId")
    val courseId: Int,

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "order")
    val order: Int,

    @ColumnInfo(name ="parentChapterID")
    val parentChapterID: Int,

    @ColumnInfo(name ="userControlSetTop")
    val userControlSetTop: Boolean,

    @ColumnInfo(name ="visible")
    val visible: Int,

)
data class TreeList(val data: ArrayList<Tree>):BaseResponse()
