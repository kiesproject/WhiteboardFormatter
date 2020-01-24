package com.example.whiteboardformatter.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TextForPreview(
    val text:String,
    val x:Int,
    val y:Int,
    val height:Int,
    val width:Int,
    val scaleX:Float,
    val scaleY:Float
): Parcelable