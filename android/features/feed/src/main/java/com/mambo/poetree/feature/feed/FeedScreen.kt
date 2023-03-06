package com.mambo.poetree.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.navigation.NavController
import com.mambo.poetree.helpers.MobileScreen

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@Composable
fun FeedScreen(
    navController: NavController
) {
    val navigateToProfile = { navController.navigate(MobileScreen.Account.route) }
    val navigateToSettings = { navController.navigate(MobileScreen.Settings.route) }
    val navigateToPoem = { navController.navigate(MobileScreen.Account.route) }
    val navigateToCompose = { navController.navigate(MobileScreen.Compose.route) }

    FeedScreenContent(
        navigateToProfile = navigateToProfile,
        navigateToSettings = navigateToSettings,
        navigateToPoem = navigateToPoem,
        navigateToCompose = { navigateToCompose.invoke() }
    )
}


@OptIn(ExperimentalUnitApi::class)
@Composable
fun FeedScreenContent(
    navigateToProfile: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToPoem: () -> Unit,
    navigateToCompose: () -> Unit,
) {
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
                text = "Feed",
                fontSize = TextUnit(24f, TextUnitType.Sp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navigateToProfile() }) {
                Text(text = "profile")
            }
            Button(onClick = { navigateToSettings() }) {
                Text(text = "settings")
            }
            Button(onClick = { navigateToCompose() }) {
                Text(text = "create")
            }
            Button(onClick = { navigateToPoem() }) {
                Text(text = "click poem")
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    val context = LocalContext.current
    FeedScreen(navController = NavController(context = context))
}