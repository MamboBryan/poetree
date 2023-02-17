package com.mambo.poetree.android.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Wed 15 Feb 2023
 */

@Composable
fun ClearTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    maxLines : Int = 4,
    onValueChanged: (String) -> Unit = {},
    leadingIcon : (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    hint: (@Composable () -> Unit)? = null,
    placeholder: @Composable () -> Unit = {},
    shape: Shape = RoundedCornerShape(0.dp),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.background,
        cursorColor = MaterialTheme.colors.primary,
        disabledLabelColor = Color.Gray,
        focusedIndicatorColor = MaterialTheme.colors.primary,
        unfocusedIndicatorColor = Color.Transparent
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
        imeAction = ImeAction.Done
    )
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        colors = colors,
        value = value,
        maxLines = maxLines,
        label = hint,
        placeholder = placeholder,
        onValueChange = onValueChanged,
        shape = shape,
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions
    )
}

@Preview
@Composable
fun ClearTextFieldPreview() {
    ClearTextField()
}