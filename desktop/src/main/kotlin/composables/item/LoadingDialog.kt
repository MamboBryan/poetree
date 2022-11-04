package com.mambo.poetree.composables.item

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog(visible: Boolean = true ,onDismiss: (() -> Unit)? = null) {
    Dialog(
        transparent = true,
        visible = visible,
        resizable = false,
        undecorated = true,
        focusable = false,
        onCloseRequest = { onDismiss?.invoke() }) {
        Column(
            modifier = Modifier.background(Color.Black.copy(alpha = 0.4f)).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card {
                CircularProgressIndicator(modifier = Modifier.padding(48.dp))
            }
        }

    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    LoadingDialog()
}