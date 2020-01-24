package com.example.whiteboardformatter.data.repository

class Repository(private val tableDao:TableDao){
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