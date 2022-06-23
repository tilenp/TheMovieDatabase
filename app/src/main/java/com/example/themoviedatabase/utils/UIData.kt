package com.example.themoviedatabase.utils

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting

class UIText(
    @get:VisibleForTesting val string: String = "",
    @get:VisibleForTesting @StringRes val stringId: Int? = null,
    @get:VisibleForTesting vararg val args: UIText = emptyArray()
) {
    fun asString(context: Context): String {
        return when {
            string.isNotBlank() -> string
            stringId != null -> context.getString(
                stringId,
                *args.map { it.asString(context) }.toTypedArray()
            )
            else -> ""
        }
    }
}

class UIPlural(
    @get:VisibleForTesting @PluralsRes val pluralId: Int? = null,
    @get:VisibleForTesting val formatArgs: String = "",
    @get:VisibleForTesting val count: Long = 0L,
) {
    fun asString(context: Context): String {
        return when (pluralId) {
            null -> ""
            else -> context.resources.getQuantityString(pluralId, count.toInt(), formatArgs)
        }
    }
}

class UIValue<T>(
    val value: T,
    val formattedValue: String = "",
)

class UISnackbar<T>(
    @StringRes val message: Int,
    @StringRes val actionLabel: Int,
    val action: T
)