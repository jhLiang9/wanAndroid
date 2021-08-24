package com.example.wanandroid.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wanandroid.entity.Article

@Dao
interface ArticleDatabaseDao {

    @Insert
    fun insert(article:Article)

    @Update
     fun update(article: Article)

    @Query("select * from home_page_article_table where id=:key")
    fun get(key:Int):Article?

    @Query("DELETE FROM home_page_article_table")
     fun clear()

    /**
     *  获取全部文章数据
     */
    @Query("select * from home_page_article_table")
    fun getAllArticles():LiveData<List<Article>>

    /**
     *  根据页数返回数据
     *  一页20条数据，每次返回20条
     *  @param page 页数
     */
    @Query("select * from home_page_article_table limit :page *20 ,20")
    fun getArticlesByPage(page:Int) :LiveData<List<Article>>
}
