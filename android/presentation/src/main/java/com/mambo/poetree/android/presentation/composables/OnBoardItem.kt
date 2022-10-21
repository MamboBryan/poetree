package com.mambo.poetree.android.presentation.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mambo.poetree.android.R

@OptIn(ExperimentalUnitApi::class)
@Composable
fun OnBoardItem(@DrawableRes id: Int, title: String, description: String) {

    val painter = painterResource(id = id)

    Column(modifier = Modifier.fillMaxSize()) {

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
                .padding(start = 24.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxSize(0.75f), text = description
            )
        }

    }

}

@Preview
@Composable
fun OnBoardItemPreview() {
    OnBoardItem(R.drawable.reader, "The Readers", "Some Description")
}