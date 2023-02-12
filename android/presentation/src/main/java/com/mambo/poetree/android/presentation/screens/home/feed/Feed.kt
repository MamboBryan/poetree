package com.mambo.poetree.android.presentation.screens.home.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */


@Composable
fun FeedScreen() {
    FeedScreenContent()
}


@Composable
fun FeedScreenContent() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier
            .fillMaxHeight(1f)
            .fillMaxWidth(), text = "Feed")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "click poem")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "create")
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    FeedScreen()
}