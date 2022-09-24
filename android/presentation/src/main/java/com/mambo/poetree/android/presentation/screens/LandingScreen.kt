package com.mambo.poetree.android.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.android.presentation.screens.destinations.AuthScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination(start = true)
@Composable
fun LandingScreen(
    navController: DestinationsNavigator
) {

    Column(
        modifier = Modifier.padding(24.dp),
    ) {

        Text(text = PoetreeApp().name())

        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = PoetreeApp().dummyPoem(),
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = PoetreeApp().dummyPoet()
            )
        }

        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navigateToAuth(navController)
                }) {
                Text(text = "Get Started")
            }
        }
    }
}

@Preview
@Composable
fun LandingScreenPreview() {

    LandingScreen(navController = EmptyDestinationsNavigator)

}

private fun navigateToAuth(navController: DestinationsNavigator) {
    navController.navigate(AuthScreenDestination)
}