package com.example.wanandroid.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.wanandroid.entity.Tree

@Dao
interface SystemDatabaseDao {
    @Insert
    fun insert(tree:Tree?)
    @Update
    fun update(tree:Tree)
    @Query("select * from tree_table ")
    fun getAllSystemTree():List<Tree>
    @Query("delete from tree_table")
    fun delete()
    @Query("select * from tree_table where id =:id ")
    fun getTree(id :Int):Tree?

}
