package com.mambo.poetree.android.ui

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Wed 15 Feb 2023
 */
object EmptyTextTooltip : TextToolbar {

    override val status: TextToolbarStatus = TextToolbarStatus.Hidden

    override fun hide() {}

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?,
    ) {
    }

}