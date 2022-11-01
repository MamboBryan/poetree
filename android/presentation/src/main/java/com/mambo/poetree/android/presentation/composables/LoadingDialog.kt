package com.mambo.poetree.android.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog(onDismiss: (() -> Unit)? = null) {
    Dialog(onDismissRequest = { onDismiss?.invoke() }) {
        Card {
            CircularProgressIndicator(modifier = Modifier.padding(48.dp),)
        }
    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    LoadingDialog()
}