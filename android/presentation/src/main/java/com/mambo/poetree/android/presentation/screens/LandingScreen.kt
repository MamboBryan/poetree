package com.mambo.poetree.android.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = PoetreeApp().name(),
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Bold,
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
                navigateToAuth(navController)
            }) {
            Text(modifier = Modifier.padding(vertical = 4.dp), text = "Get Started")
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