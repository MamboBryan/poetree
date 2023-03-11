package com.mambo.poetree.utils

import kotlin.math.ln
import kotlin.math.pow

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 11 Mar 2023
 */
fun Long.prettyCount(): String {
    if (this < 1000) return "$this"
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return "${this / 1000.0.pow(exp.toDouble())} ${"kMGTPE"[exp - 1]}"
}