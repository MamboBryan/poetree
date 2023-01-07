package com.mambo.poetree.utils

import kotlinx.serialization.Serializable

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Fri 06 Jan 2023
 */
enum class DialogType {
    NORMAL, SUCCESS, ERROR
}

@Serializable
data class DialogData(
    val type: DialogType = DialogType.NORMAL,
    val title: String = "Alert",
    val description: String,
    val confirm: (() -> Unit)? = null,
    val dismiss: (() -> Unit)? = null
)