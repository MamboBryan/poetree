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
    val positiveText: String = "ok",
    val negativeText: String = "dismiss",
    val positiveAction: (() -> Unit)? = null,
    val negativeAction: (() -> Unit)? = null
)