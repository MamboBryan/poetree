package com.mambo.poetree.android.ui

import androidx.compose.ui.graphics.Color

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 19 Feb 2023
 */
fun String.asColor() = Color(android.graphics.Color.parseColor(this))