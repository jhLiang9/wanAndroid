package com.example.wanandroid.entity.list

import com.example.wanandroid.entity.Tree

data class TreeList(val data: ArrayList<Tree>, val errorCode: Int = 0, val errorMessage: String = "")
