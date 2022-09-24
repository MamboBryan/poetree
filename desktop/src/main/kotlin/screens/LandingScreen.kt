package com.mambo.poetree.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

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