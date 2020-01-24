package com.example.whiteboardformatter.data.repository

import com.example.whiteboardformatter.data.dao.TextDao

class Repository(private val textDao:TextDao){
    fun getAll():List<Whiteboard>{
        return tableDao.getAll()
    }
    fun getById(whiteboardId:Long):Table{
        return tableDao.getById(whiteboardId)
    }
    fun insert(text:Text){
        tableDao.insert(text)
    }
    fun insert(whiteboard:Whiteboard){
        tableDao.insert(whiteboard)
    }
    fun update(text:Text){
        tableDao.update(text)
    }
}