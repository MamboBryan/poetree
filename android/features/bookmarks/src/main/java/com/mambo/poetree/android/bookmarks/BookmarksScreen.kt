package com.mambo.poetree.android.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.navigation.NavController

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@Composable
fun BookmarkScreen(navController: NavController) {
    BookmarkScreenContent(navController = navController)
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun BookmarkScreenContent(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bookmark",
                fontSize = TextUnit(24f, TextUnitType.Sp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "click bookmark")
            }
        }
    }
}

@Preview
@Composable
fun BookmarkScreenPreview() {
    val context = LocalContext.current
    BookmarkScreen(navController = NavController(context = context))
}