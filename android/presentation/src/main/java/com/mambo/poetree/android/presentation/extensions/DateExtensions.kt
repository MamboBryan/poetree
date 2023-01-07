package com.mambo.poetree.android.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*
import android.icu.util.Calendar

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Thu 05 Jan 2023
 */
private fun Date?.toDateStringPattern(pattern: String): String {
    val outputDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return if (this == null) "" else outputDateFormat.format(this)
}

fun Date?.toString(pattern: String): String {
    return this.toDateStringPattern(pattern)
}

private fun Calendar?.toDateStringByPattern(pattern: String): String {
    return this?.time.toDateStringPattern(pattern)
}

fun Calendar?.toString(pattern: String): String? {
    return this?.let {
        it.toDateStringByPattern(pattern)
    }
}