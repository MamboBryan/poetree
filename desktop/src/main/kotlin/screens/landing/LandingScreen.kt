package com.mambo.poetree.screens.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.composables.section.AccountSection
import com.mambo.poetree.composables.section.AuthSection
import com.mambo.poetree.composables.section.GetStartedSection
import com.mambo.poetree.composables.section.OnBoardingSection
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.extensions.slideInLeft
import com.mambo.poetree.extensions.slideInRight
import com.mambo.poetree.extensions.slideOutLeft
import com.mambo.poetree.extensions.slideOutRight
import com.mambo.poetree.navigation.NavController

private enum class Landing {
    GET_STARTED,
    AUTHENTICATION,
    SETUP
}

@Composable
fun LandingScreen(
    navController: NavController
) {

    var section by remember { mutableStateOf(Landing.GET_STARTED) }
    val signedIn = UserPreferences().signedIn.collectAsState(initial = false)
    val hasSetup = UserPreferences().isUserSetup.collectAsState(initial = false)

    Row {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            OnBoardingSection()
        }

        Column(
            modifier = Modifier.padding(24.dp).weight(1f),
        ) {

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource("icons/logo.png"), contentDescription = null)
                Text(text = PoetreeApp().name(), fontWeight = FontWeight.Bold)

            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(
                    visible = section == Landing.GET_STARTED,
                    enter = slideInLeft(),
                    exit = slideOutLeft()
                ) {
                    GetStartedSection { section = Landing.AUTHENTICATION }
                }

                AnimatedVisibility(
                    visible = section == Landing.AUTHENTICATION,
                    enter = slideInRight(),
                    exit = slideOutLeft()
                ) {
                    AuthSection(navController = navController)
                }

                AnimatedVisibility(
                    visible = section == Landing.SETUP,
                    enter = slideInRight(),
                    exit = slideOutRight()
                ) {
                    AccountSection(navController = navController)
                }

            }

        }

    }
}

@Preview
@Composable
fun LandingScreenPreview() {
    LandingScreen(navController = NavController(""))
}
