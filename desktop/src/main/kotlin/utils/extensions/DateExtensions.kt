package utils.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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

fun String.toDate(pattern: String): Date? {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return try {
        format.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun String.toDateStringByPattern(input: String, output: String): String? {
    val date = this.toDate(input)
    return date?.let { it.toString(pattern = output) }
}