package com.example.wanandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wanandroid.database.dao.SystemDatabaseDao
import com.example.wanandroid.entity.Tree
import com.example.wanandroid.entity.converter.TreeConverter

@Database(entities = [Tree::class],version = 2,exportSchema = false)
@TypeConverters(TreeConverter::class)
abstract class SystemDatabase :RoomDatabase(){
    abstract val systemDatabaseDao: SystemDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE:SystemDatabase? = null
        fun getInstance(context: Context):SystemDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(context.applicationContext,SystemDatabase::class.java,"system_table")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE=instance
                }
                return instance
            }
        }

    }


}