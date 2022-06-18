package com.example.themoviedatabase.utils

fun Int.toHourMinutes(): String {
    val hours = this.div(60)
    val minutes = this % 60
    return String.format("%d:%02d", hours, minutes)
}