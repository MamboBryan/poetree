package com.mambo.poetree.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.navigation.NavController

@OptIn(ExperimentalUnitApi::class)
@Composable
fun LandingScreen(
    navController: NavController?
) {

    Row {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            OnBoardingScreen()
        }

        Column(
            modifier = Modifier.padding(24.dp).weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column {
                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = PoetreeApp().name(),
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(24f, TextUnitType.Sp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = PoetreeApp().dummyPoem(),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = PoetreeApp().dummyPoet(),
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    }) {
                    Text(modifier = Modifier.padding(vertical = 4.dp), text = "Get Started")
                }
            }


        }

    }
}

@Preview
@Composable
fun LandingScreenPreview() {

    LandingScreen(null)

}
