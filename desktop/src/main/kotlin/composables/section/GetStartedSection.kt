package com.mambo.poetree.composables.section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.mambo.poetree.PoetreeApp

@OptIn(ExperimentalUnitApi::class)
@Composable
fun GetStartedSection(show: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier,
            text = PoetreeApp().dummyPoem(),
            style = MaterialTheme.typography.body1,
            fontSize = TextUnit(24f, TextUnitType.Sp),
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = PoetreeApp().dummyPoet(),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start
        )
        Button(
            modifier = Modifier.padding(top = 24.dp),
            onClick = show
        ) {
            Text(modifier = Modifier.padding(vertical = 4.dp), text = "Get Started")
        }
    }

}

@Preview
@Composable
fun GetStartedSectionPreview() {
    GetStartedSection { }
}