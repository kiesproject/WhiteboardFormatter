package com.example.whiteboardformatter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yabamiru.data.model.Task
import com.example.yabamiru.data.model.TaskAndTaskTags

@Dao
interface TableDao{

    @Query("SELECT * FROM whiteboard")
    fun getAll():LiveData<List<Whiteboard>>

    @Query("SELECT * FROM Text WHERE whiteboardId=(:whiteboardId)")
    fun getById(whiteboardId:Long):LiveData<List<Text>>

    @Insert
    fun insert(vararg text:Text)

    @Insert
    fun insert(whiteboard:Whiteboard):Long

    @Update
    fun update(text:Text)

    @Query("DELETE FROM Text WHERE whiteboardId=(:WhiteboardId)")
    fun delete(WhiteboardId:Long)

    @Query("DELETE FROM Text WHERE id=(:textId)")
    fun delete(textId:Long)
}