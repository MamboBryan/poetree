package com.mambo.poetree.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.composables.OnBoardItem

@Composable
fun OnBoardingScreen() {

    var page by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val (title, description) = PoetreeApp().onBoardingDetails()[page]
        val path = "images/".plus(
            when (page) {
                0 -> "reader.png"
                1 -> "poet.png"
                else -> "community.png"
            }
        )

        Column(modifier = Modifier.weight(0.9f)) {
            OnBoardItem(
                modifier = Modifier,
                path = path,
                title = title,
                description = description
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().weight(0.1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                Icons.Rounded.ChevronLeft,
                "previous",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        if (page == 0)
                            page = 2
                        else
                            page -= 1
                    })
            Icon(
                Icons.Rounded.ChevronRight,
                "next",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        if (page == 2)
                            page = 0
                        else
                            page += 1
                    })

        }

    }

}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen()
}