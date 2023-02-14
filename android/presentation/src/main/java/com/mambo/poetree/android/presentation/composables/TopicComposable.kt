package com.mambo.poetree.android.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue 14 Feb 2023
 */

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TopicLarge(modifier: Modifier = Modifier, name: String, color: Color) {
    Card(
        modifier = modifier.defaultMinSize(minWidth = 300.dp, minHeight = 200.dp),
        backgroundColor = color,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.Start) {
            Text(
                modifier = Modifier.padding(start = 24.dp, bottom = 24.dp),
                text = name.replaceFirstChar { it.uppercase() },
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
        }
    }
}

@Preview
@Composable
fun TopicLargePreview() {
    TopicLarge(name = "joy", color = Color.Blue)
}