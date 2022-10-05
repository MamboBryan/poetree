package com.mambo.poetree.extensions

import androidx.compose.ui.window.FrameWindowScope
import java.awt.Dimension

fun FrameWindowScope.setMinimumSize(width: Int = 900, height: Int = 600) {
    this.window.minimumSize = Dimension(width, height)
}