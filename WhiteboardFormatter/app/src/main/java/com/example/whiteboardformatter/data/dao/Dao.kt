package com.example.whiteboardformatter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yabamiru.data.model.Task
import com.example.yabamiru.data.model.TaskAndTaskTags

@Dao
interface TableDao{

    @Query("SELECT * FROM Table")
    fun getAll():LiveData<List<Table>>

    @Query("SELECT * FROM Table WHERE whiteboardId=(:whiteboardId)")
    fun getById(whiteboardId:Long):LiveData<Task>

    @Insert
    fun insert(vararg text:text):Text<String>

    @Insert
    fun insert(vararg whiteboard:text):Whiteboard<String>

    @Update
    fun update(text:text)

    @Query("DELETE FROM Table WHERE whiteboard=(:Whiteboard)")
    fun delete(Whiteboard:String)

    @Query("DELETE FROM Table WHERE text=(:Text)")
    fun delete(Text:String)
}