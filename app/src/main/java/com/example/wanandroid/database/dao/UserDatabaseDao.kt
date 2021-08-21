package com.example.wanandroid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wanandroid.entity.User

@Dao
interface UserDatabaseDao {
    @Insert
    suspend fun insert(user:User)

    @Update
    suspend fun update(user:User)

    @Query("delete from user_table")
    suspend fun delete()


}