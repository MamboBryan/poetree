package com.mambo.poetree.android.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 04 Mar 2023
 */

@Composable
fun CommentField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    isButtonEnabled: Boolean = true,
    isProgressVisible: Boolean = false,
    onClick : () -> Unit = {}
) {

    Surface(
        elevation = 8.dp,
        color = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            ) {
            ClearTextField(
                singleLine = false,
                value = value,
                onValueChanged = onValueChanged,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type comment ...") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedBorderColor = MaterialTheme.colors.surface,
                    unfocusedBorderColor = MaterialTheme.colors.surface
                ),
                trailingIcon = {
                    AnimatedVisibility(visible = isProgressVisible) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }

                    AnimatedVisibility(visible = isProgressVisible.not()) {
                        IconButton(
                            modifier = Modifier,
                            enabled = isButtonEnabled,
                            onClick = onClick
                        ) {
                            Icon(
                                tint = if (isButtonEnabled) MaterialTheme.colors.primary else Color.Gray,
                                imageVector = Icons.Rounded.Send,
                                contentDescription = "send"
                            )
                        }
                    }

                }
            )
        }
    }

}

@Preview
@Composable
fun CommentFieldPreview() {
    CommentField(
        value = "jkdnqiwfnibrfbruvbrhuvubvutbviubwbviwbelvnwljknviutvburbuvhubrhvblbwvblhbuhfvwhburb4uwbqebicjibwibfcjkbwijebviubruivbnwekbvnijwvnuwbriuvbrjkbviuwerbuvbribvrbuvbrivuiwbnlvnewniunqeiondijnwuhbacusdbcubruvburtbvwiurbvnkws",
        onValueChanged = {})
}