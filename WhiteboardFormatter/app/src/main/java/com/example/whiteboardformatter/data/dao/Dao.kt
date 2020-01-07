package com.example.whiteboardformatter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yabamiru.data.model.Task
import com.example.yabamiru.data.model.TaskAndTaskTags

@Dao
interface TableDao{

    @Query("SELECT * FROM whiteboard")
    fun getAll():LiveData<List>

    @Query("SELECT * FROM Text WHERE whiteboardId=(:whiteboardId)")
    fun getById(whiteboardId:Long):LiveData<List>

    @Insert
    fun insert(UpperCamel text:Text)

    @Insert
    fun insert(Whiteboard:text):Whiteboard<String>

    @Update
    fun update(UpperCamel text:Text)

    @Query("DELETE FROM Table WHERE whiteboardId=(:WhiteboardId)")
    fun delete(WhiteboardId:String)

    @Query("DELETE FROM Table WHERE id=(:textId)")
    fun delete(textId:Long)
}