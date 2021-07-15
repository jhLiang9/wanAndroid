package com.example.wanandroid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wanandroid.entity.Article

@Dao
interface ArticleDatabaseDao {
    @Insert
    suspend fun insert(article:Article)

    @Update
    suspend fun update(article: Article)

    @Query("select * from home_page_article_table where id=:key")
    suspend fun get(key:Int):Article

    @Query("DELETE FROM home_page_article_table")
    suspend fun clear()

    @Query("select * from home_page_article_table")
    fun getAllArticles():LiveData<List<Article>>
}