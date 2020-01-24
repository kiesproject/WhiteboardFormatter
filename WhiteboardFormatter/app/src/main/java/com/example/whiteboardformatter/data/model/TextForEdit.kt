package com.example.whiteboardformatter.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TextForEdit(
    val text:String,
    val x:Int,
    val y:Int
):Parcelable