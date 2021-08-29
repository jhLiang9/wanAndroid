package com.example.wanandroid.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserConverter {
    @TypeConverter
    fun objectToString(list: ArrayList<Any>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToObject(json: String): ArrayList<Any> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Any>>() {}.type
        return gson.fromJson(json, type)
    }
}