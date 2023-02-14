package com.mambo.poetree.android.presentation.utils

import androidx.compose.ui.graphics.Color

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue 14 Feb 2023
 */

fun String.asColor() =  Color(android.graphics.Color.parseColor(this))