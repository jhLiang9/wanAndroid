package com.example.wanandroid.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.wanandroid.entity.converter.ArticleConverter
import kotlin.collections.ArrayList

/**
 * 首页文章
 */
@Entity(tableName = "home_page_article_table")
@TypeConverters(ArticleConverter::class)
data class Article (
    val apkLink:String ="",
    val audit :Int,
    @ColumnInfo(name="author")
    val author:String="",
    val canEdit :Boolean= false,
    val chapterId :Int ,
    val chapterName :String ="未分类",
    val collect :Boolean = false,
    val courseId :Int ,
    @ColumnInfo(name = "description")
    val desc:String = "",
    val descMD:String ?="",
    val envelopePic:String ="",
    val fresh :Boolean ,
    val host:String = "",
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val link:String="",
    val niceDate:String ="",
    val niceShareDate:String="",
    val origin:String="",
    val prefix:String="",
    val projectLink:String = "",
    //时间戳
    val publishTime:Long,
    val realSuperChapterID:Int,
    val selfVisible :Int,
    //时间戳
    val shareDate :Long,
    val shareUser:String ="",
    val superChapterId :Int,
    @ColumnInfo(name = "superChapterName")
    val superChapterName:String="",
    val tags:ArrayList<Any>,
    @ColumnInfo(name = "title")
    val title:String,
    val type:Int = 0,
    val userId:Int,
    val visible :Int,
    val zan:Int

)

data class ArticleList(val data: ArticleData):BaseResponse()
data class ArticleData(
    val curPage: Int,
    val datas: ArrayList<Article>,
    val over: Boolean = false,
    val pageCount: Int,
    val size: Int,
    val total: Int
)