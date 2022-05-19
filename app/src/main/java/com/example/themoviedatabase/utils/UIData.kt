package com.example.themoviedatabase.utils

import android.content.Context
import androidx.annotation.StringRes

class UIText(
    val string: String = "",
    @StringRes private val stringId: Int? = null,
    vararg val args: UIText = emptyArray()
) {
    fun asString(context: Context): String {
        return when  {
            string.isNotBlank() -> string
            stringId != null -> context.getString(stringId, *args.map { it.asString(context) }.toTypedArray())
            else -> ""
        }
    }
}