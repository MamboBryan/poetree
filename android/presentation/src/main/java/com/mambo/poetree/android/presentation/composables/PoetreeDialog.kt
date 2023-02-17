package com.mambo.poetree.android.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mambo.poetree.android.presentation.utils.dismissDialog
import com.mambo.poetree.utils.DialogData

@Composable
fun PoetreeDialog(
    data: DialogData
) {

    val dismiss = data.negativeAction
    val confirm = data.positiveAction

    AlertDialog(
        onDismissRequest = {
            dismiss?.invoke()
            dismissDialog()
        },
        title = {
            Text(text = data.title)
        },
        text = {
            Text(text = data.description)
        },
        confirmButton = {
            if (confirm != null)
                TextButton(
                    onClick = {
                        confirm.invoke()
                        dismissDialog()
                    }) {
                    Text(text = data.positiveText, modifier = Modifier.padding(4.dp))
                }
        },
        dismissButton = {
            if (dismiss != null)
                TextButton(
                    onClick = {
                        dismiss.invoke()
                        dismissDialog()
                    }) {
                    Text(modifier = Modifier.padding(4.dp), text = data.negativeText)
                }
        }
    )
}

@Preview
@Composable
fun PoetreeDialogPreview() {
    PoetreeDialog(data = DialogData(description = "This is me alerting"))
}