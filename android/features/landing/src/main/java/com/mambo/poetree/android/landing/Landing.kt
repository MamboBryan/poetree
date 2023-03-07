package com.mambo.poetree.android.landing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.android.ui.navigation.MobileScreen

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@Composable
fun LandingScreen(navController: NavController) {

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
    val context = LocalContext.current
    LandingScreen(navController = NavController(context = context))
}

private fun navigateToAuth(navController: NavController) {
    navController.navigate(MobileScreen.Authentication.route) {
        popUpTo(MobileScreen.Authentication.route) { inclusive = true }
    }
}