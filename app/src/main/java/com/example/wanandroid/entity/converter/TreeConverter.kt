package com.example.wanandroid.entity.converter

import androidx.room.TypeConverter
import com.example.wanandroid.entity.Tree
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList

class TreeConverter {
        @TypeConverter
        fun objectToString(list: ArrayList<Tree>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        fun stringToObject(json: String): ArrayList<Tree> {
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Tree>>() {}.type
            return gson.fromJson(json, type)
        }
}