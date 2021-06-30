package com.example.wanandroid.utils

object HtmlElementUtil {

    fun removeHTMLTag(str:String):String {
        val regex:Regex= Regex("</?[a-zA-Z]*>")
        var temp :String=str.replace(regex,"")
        temp.replace("&nbsp","")
        return temp;
    }
}