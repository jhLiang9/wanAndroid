package com.example.wanandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wanandroid.database.dao.ArticleDatabaseDao
import com.example.wanandroid.entity.Article

@Database(entities = [Article::class],version = 1,exportSchema = false)
abstract class ArticleDatabase :RoomDatabase(){
    abstract val articleDatabaseDao: ArticleDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE:ArticleDatabase? = null
    }

    fun getInstance(context: Context):ArticleDatabase{
        synchronized(this){
            var instance = INSTANCE
            if(instance==null){
                instance= Room.databaseBuilder(context.applicationContext,ArticleDatabase::class.java,"home_page_article_table")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
            }
            return instance
        }
    }
}