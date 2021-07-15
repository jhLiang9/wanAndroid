package com.example.wanandroid.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_page_article_table")
data class Article(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo(name = "title")
    val title:String,

    @ColumnInfo(name="author")
    val author:String,

    @ColumnInfo(name = "time")
    val time:String,

    @ColumnInfo(name = "superChapterName")
    val superChapterName:String,

    @ColumnInfo(name = "url")
    val url:String,

    @ColumnInfo(name = "description")
    val description:String) {

}
