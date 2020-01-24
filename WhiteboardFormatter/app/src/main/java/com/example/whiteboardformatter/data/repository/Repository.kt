package com.example.whiteboardformatter.data.repository

import com.example.whiteboardformatter.data.dao.TableDao

class Repository(textDao:TableDao){
    fun getAll():List<Whiteboard>{
        return textDao.getAll()
    }
    fun getById(whiteboardId:Long):Table{
        return textDao.getById(whiteboardId)
    }
    fun insert(text:Text){
        textDao.insert(text)
    }
    fun insert(whiteboard:Whiteboard){
        textDao.insert(whiteboard)
    }
    fun update(text:Text){
        textDao.update(text)
    }
}