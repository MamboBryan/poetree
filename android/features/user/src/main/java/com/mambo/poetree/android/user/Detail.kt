package com.mambo.poetree.android.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.mambo.poetree.utils.prettyCount

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue 14 Mar 2023
 */
@OptIn(ExperimentalUnitApi::class)
@Composable
fun Detail(modifier: Modifier = Modifier, title: String, count: Long) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.alpha(0.8f),
            text = title
        )
        Text(
            text = count.prettyCount(),
            fontSize = TextUnit(24f, TextUnitType.Sp),
        )
    }
}

@Preview
@Composable
fun DetailPreview() {
    Detail(title = "Reads", count = 1200)
}