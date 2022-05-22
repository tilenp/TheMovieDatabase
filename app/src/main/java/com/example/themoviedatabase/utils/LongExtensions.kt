package com.example.themoviedatabase.utils

import kotlin.math.ln
import kotlin.math.pow

fun Long.thousandFormat(): String {
    if (this < 1000) {
        return toString()
    }
    val exp = ln(this.toDouble()).div(ln(1000.0)).toInt()
    return String.format("%.1f %c", this.div(1000.0.pow(exp)), "kMBTPE"[exp - 1])
}