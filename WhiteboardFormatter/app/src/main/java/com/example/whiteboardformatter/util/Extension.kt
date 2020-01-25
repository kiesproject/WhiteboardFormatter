@file:Suppress("UNREACHABLE_CODE")

package com.example.whiteboardformatter.util

import java.lang.IllegalArgumentException

// from < to の環境下で使うこと
fun Float.until(to:Float):ClosedFloatingPointRange<Float>{
    if(to<= Float.MIN_VALUE) return throw IllegalArgumentException("The given  argument is out of Float Range")
    return this .. (to + Float.MIN_VALUE)
}