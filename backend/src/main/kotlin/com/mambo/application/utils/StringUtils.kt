package com.mambo.application.utils

import io.github.aakira.napier.Napier
import java.util.*

fun String?.isValidEmail(): Boolean {
    if (this.isNullOrBlank()) return false
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return emailRegex.toRegex().matches(this)
}

fun String?.isValidPassword(): Boolean {
    if (this.isNullOrBlank()) return false
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    return passwordRegex.toRegex().matches(this) and (this.length >= 8)
}

fun String?.isValidUrl(): Boolean {
    if (this.isNullOrBlank()) return false
    val urlRegex = ("((http|https)://)(www.)?"
            + "[a-zA-Z0-9@:%._\\+~#?&//=]"
            + "{2,256}\\.[a-z]"
            + "{2,6}\\b([-a-zA-Z0-9@:%"
            + "._\\+~#?&//=]*)")
    return urlRegex.toRegex().matches(this)
}

fun String?.isValidHexColor(): Boolean {
    if (this.isNullOrBlank()) return false
    val hexRegex = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})\$"
    return hexRegex.toRegex().matches(this)
}

fun String?.asUUID(): UUID? {
    if (this.isNullOrBlank()) return null
    return try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        Napier.e(e.localizedMessage, e)
        null
    }
}

fun UUID?.asString(): String? = this?.toString()