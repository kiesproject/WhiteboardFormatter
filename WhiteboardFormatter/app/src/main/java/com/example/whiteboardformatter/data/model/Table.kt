package com.example.whiteboardformatter.data.model

import androidx.room.*
import java.util.*

@Entity
data class Whiteboard constructor(
        @PrimaryKey(autoGenerate = true)
        var id : Long=0,
        val timestump : Long = Date().time
)
data class Text constructor(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var whiteboardId: Long,
        var text: String = "",
        var x: Integer,
        var y: Integer,
        var width: Integer,
        var height: Integer
)





