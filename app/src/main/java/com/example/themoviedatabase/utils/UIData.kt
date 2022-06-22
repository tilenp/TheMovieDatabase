package com.example.themoviedatabase.utils

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting

class UIText(
    private val string: String = "",
    @StringRes private val stringId: Int? = null,
    private vararg val args: UIText = emptyArray()
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UIText

        if (string != other.string) return false
        if (stringId != other.stringId) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = string.hashCode()
        result = 31 * result + (stringId ?: 0)
        result = 31 * result + args.contentHashCode()
        return result
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UIPlural

        if (pluralId != other.pluralId) return false
        if (formatArgs != other.formatArgs) return false
        if (count != other.count) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pluralId ?: 0
        result = 31 * result + formatArgs.hashCode()
        result = 31 * result + count.hashCode()
        return result
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