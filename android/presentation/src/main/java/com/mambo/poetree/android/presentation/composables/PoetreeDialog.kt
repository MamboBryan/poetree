package com.mambo.poetree.android.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PoetreeDialog(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    onConfirm: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss?.invoke() },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            if (onConfirm != null)
                OutlinedButton(
                    onClick = {

                    }) {
                    Text(text = "confirm", modifier = Modifier.padding(4.dp))
                }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss?.invoke()
                }) {
                Text(modifier = Modifier.padding(4.dp), text = "dismiss")
            }
        }
    )
}

@Preview
@Composable
fun PoetreeDialogPreview() {
    PoetreeDialog(title = "Alert", message = "This is an alerting")
}