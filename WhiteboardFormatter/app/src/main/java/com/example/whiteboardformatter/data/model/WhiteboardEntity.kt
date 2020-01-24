package com.example.whiteboardformatter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class WhiteboardEntity constructor(
    @PrimaryKey(autoGenerate = true)
    var id : Long=0,
    val timestump : Long = Date().time
)