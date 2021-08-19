package com.example.wanandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wanandroid.entity.Tree

@Dao
interface SystemDatabaseDao {
    @Insert
    fun insert(tree:Tree)
    @Update
    fun update(tree:Tree)
    @Query("select * from tree where ")
    fun getAllSystemTree()

}