package com.example.wanandroid.entity

class Project(val children: Project?, val courseId:Int, val id:Int, val name:String,
              val order:Int, val parentChapterID:Int, val userControlSetTop:Boolean, val visible:Int) {

}