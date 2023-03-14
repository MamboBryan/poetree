package com.mambo.poetree.android.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Thu 09 Mar 2023
 */
@Composable
fun HorizontalStat(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    count: Long,
    color: Color = MaterialTheme.colors.onSurface,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .width(12.dp)
                .height(12.dp),
            imageVector = icon,
            contentDescription = "icon",
            tint = color
        )
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            text = count.toString(),
            color = color
        )
    }
}

@Composable
fun VerticalStat(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    count: Long,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = "icon"
            )
        }
        Text(modifier = Modifier.padding(bottom = 8.dp), text = count.toString())
    }
}

@Preview
@Composable
fun HorizontalStatPreview() {
    HorizontalStat(icon = Icons.Rounded.CheckCircle, count = 2000)
}

@Preview
@Composable
fun VerticalStatPreview() {
    VerticalStat(icon = Icons.Rounded.CheckCircle, count = 2000)
}