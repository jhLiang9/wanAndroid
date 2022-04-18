package com.example.wanandroid.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
data class TreeList(val data: ArrayList<Tree>): BaseResponse()
