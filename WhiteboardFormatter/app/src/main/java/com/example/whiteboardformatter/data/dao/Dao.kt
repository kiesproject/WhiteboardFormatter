package com.example.whiteboardformatter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.whiteboardformatter.data.model.Text
import com.example.whiteboardformatter.data.model.WhiteboardEntity

@Dao
interface TableDao{

    @Query("SELECT * FROM whiteboardentity")
    fun getAll():LiveData<List<WhiteboardEntity>>

    @Query("SELECT * FROM Text WHERE whiteboardId=(:whiteboardId)")
    fun getById(whiteboardId:Long):LiveData<List<Text>>

    @Query("DELETE FROM Text WHERE whiteboardId=(:whiteboardId)")
    fun delete(whiteboardId: Long)

    @Query("DELETE FROM Text WHERE id=(:textId)")
    fun deleteText(textId:Long)

    @Insert
    fun insert(vararg text:Text)

    @Insert
    fun insert(whiteboard:WhiteboardEntity):Long

    @Update
    fun update(text:Text)


}