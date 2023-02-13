package com.mambo.poetree.android.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.mambo.poetree.android.presentation.screens.destinations.PoemScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ProfileScreenContent(navigator: DestinationsNavigator) {
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
                text = "Profile",
                fontSize = TextUnit(24f, TextUnitType.Sp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navigator.popBackStack() }) {
                Text(text = "back")
            }
            Button(onClick = { navigator.navigate(PoemScreenDestination) }) {
                Text(text = "navigate to poem")
            }
        }
    }
}

@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator
) {
    ProfileScreenContent(navigator = navigator)
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navigator = EmptyDestinationsNavigator)
}