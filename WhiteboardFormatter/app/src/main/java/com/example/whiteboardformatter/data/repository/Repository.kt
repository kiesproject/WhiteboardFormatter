package com.example.whiteboardformatter.data.repository

import androidx.lifecycle.LiveData
import com.example.whiteboardformatter.data.dao.TableDao
import com.example.whiteboardformatter.data.model.Text
import com.example.whiteboardformatter.data.model.WhiteboardEntity

class Repository(private val textDao:TableDao){
    fun getAll():LiveData<List<WhiteboardEntity>>{
        return textDao.getAll()
    }
    fun getById(whiteboardId:Long):LiveData<List<Text>>{
        return textDao.getById(whiteboardId)
    }
    fun insert(text: Text){
        textDao.insert(text)
    }
    fun insert(whiteboard:WhiteboardEntity):Long{
        return textDao.insert(whiteboard)
    }
    fun update(text:Text){
        textDao.update(text)
    }
}