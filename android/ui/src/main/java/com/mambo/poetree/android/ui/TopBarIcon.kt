package com.mambo.poetree.android.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue 14 Feb 2023
 */
@Composable
fun TopBarIcon(
    imageVector: ImageVector = Icons.Rounded.ArrowBack,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}