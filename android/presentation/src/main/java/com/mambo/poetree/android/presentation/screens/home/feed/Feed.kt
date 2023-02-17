package com.mambo.poetree.android.presentation.screens.home.feed

import androidx.compose.foundation.layout.*
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
import com.mambo.poetree.android.presentation.screens.destinations.ComposeScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.PoemScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.ProfileScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

@Destination
@Composable
fun FeedScreen(
    navigator: DestinationsNavigator
) {

    val navigateToProfile = { navigator.navigate(ProfileScreenDestination) }
    val navigateToSettings = { navigator.navigate(SettingsScreenDestination) }
    val navigateToPoem = { navigator.navigate(PoemScreenDestination) }
    val navigateToCompose = { navigator.navigate(ComposeScreenDestination) }

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
    FeedScreen(navigator = EmptyDestinationsNavigator)
}