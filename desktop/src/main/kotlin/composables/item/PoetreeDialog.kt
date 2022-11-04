package com.mambo.poetree.composables.item

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
@Composable
fun PoetreeDialog(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    onConfirm: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(0.4f),
        onDismissRequest = { onDismiss?.invoke() },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top=70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "\uD83E\uDD70", fontSize = TextUnit(36f, TextUnitType.Sp))
                Text(modifier = Modifier.padding(top = 8.dp), text = title)
            }
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            if (onConfirm != null)
                OutlinedButton(
                    onClick = {
                        onConfirm.invoke()
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