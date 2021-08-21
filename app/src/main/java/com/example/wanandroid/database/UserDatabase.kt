package com.example.wanandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wanandroid.database.dao.UserDatabaseDao
import com.example.wanandroid.entity.User

@Database(entities = [User::class],version = 1 ,exportSchema = false)
abstract class UserDatabase :RoomDatabase(){
    abstract val userDatabaseDao :UserDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null
        fun getInstance(context: Context):UserDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        ""
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}