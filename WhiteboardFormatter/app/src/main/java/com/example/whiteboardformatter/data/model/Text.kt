package com.example.whiteboardformatter.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = WhiteboardEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("whiteboardId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Text constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var whiteboardId: Long,
    var text: String = "",
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    var xScale:Float,
    var yScale:Float
)