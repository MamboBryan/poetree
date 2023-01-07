package com.mambo.poetree.android.presentation.utils

import androidx.compose.runtime.compositionLocalOf
import com.mambo.poetree.utils.DialogData

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 07 Jan 2023
 */
val LocalDialog = compositionLocalOf<DialogData?> { null }