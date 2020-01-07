package com.example.whiteboardformatter.data.model

import androidx.room.*
import java.util.*

@Entity(
        data class WhiteboardEntity constructor(
                @PrimaryKey(autoGenerate = true)
                var id : Long=0,
                val timestump : Long = Date().time
        )
)
@Entity(
        data class Text constructor(
                @PrimaryKey(autoGenerate = true)
                var id: Long = 0,
                var whiteboardId: Long,
                var text: String = "",
                var x: Int,
                var y: Int,
                var width: Int,
                var height: Int
        )
)




