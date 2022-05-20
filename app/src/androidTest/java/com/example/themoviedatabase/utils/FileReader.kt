package com.example.themoviedatabase.utils

import android.content.Context

object FileReader {

    fun readFile(context: Context, fileName: String): String {
        return context.resources.openRawResource(
            context.resources.getIdentifier(fileName, "raw", context.packageName)
        ).bufferedReader()
            .use { reader -> reader.readText() }
    }
}