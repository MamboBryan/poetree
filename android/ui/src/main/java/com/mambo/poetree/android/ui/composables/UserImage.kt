package com.mambo.poetree.android.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Fri 10 Mar 2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserImage(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.primary),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "MB",
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
fun UserImagePreview() {
    UserImage()
}