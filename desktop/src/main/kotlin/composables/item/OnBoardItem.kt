package com.mambo.poetree.composables.item

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalUnitApi::class)
@Composable
fun OnBoardItem(modifier: Modifier = Modifier, path: String, title: String, description: String) {

    val painter = painterResource(resourcePath = path)

    Column {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f),
            alignment = Alignment.Center,
            painter = painter,
            contentDescription = "some image"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(0.8f),
                    textAlign = TextAlign.Center,
                    text = description
                )
            }

        }

    }

}

@Preview
@Composable
fun OnBoardItemPreview() {
    OnBoardItem(modifier = Modifier, "images/racoon.png", "The Readers", "Some Description")
}