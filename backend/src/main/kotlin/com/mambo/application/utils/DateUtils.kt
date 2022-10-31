package com.mambo.application.utils

import io.github.aakira.napier.Napier
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

val dateFormat = SimpleDateFormat("dd-MM-yyyy").also { it.isLenient = false }
val dateTimeFormat = SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss.SSSZ").also { it.isLenient = false }

fun String?.toDate(): Date? {
    if (this.isNullOrBlank()) return null
    return try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

fun Long?.toDateTimeString(): String? {
    if (this == null) return null
    return try {
        dateFormat.format(this)
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

fun Date?.toDateString(): String? {
    if (this == null) return null
    return try {
        dateFormat.format(this)
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

fun Date?.toDateTimeString(): String? {
    if (this == null) return null
    return try {
        dateTimeFormat.format(this)
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

fun Date?.asLocalDate(): LocalDate? {
    if (this == null) return null
    return try {
        Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDate()
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

fun Date.isValidAge(): Boolean {
    val now = Calendar.getInstance()
    val then = Calendar.getInstance()
    then.time = this
    val years = now[Calendar.YEAR] - then[Calendar.YEAR]
    return years >= 15
}

fun LocalDate?.toDate(): Date? {
    if (this == null) return null
    return try {
        Date.from(this.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

fun LocalDateTime?.toDate(): Date? {
    if (this == null) return null
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}
