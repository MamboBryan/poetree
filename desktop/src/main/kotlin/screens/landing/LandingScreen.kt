package com.mambo.poetree.screens.landing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {
                    GetStartedSection { section = Landing.AUTHENTICATION }
                }

                AnimatedVisibility(
                    visible = section == Landing.AUTHENTICATION,
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {
                    AuthSection(navController = navController)
                }

                AnimatedVisibility(
                    visible = section == Landing.SETUP,
                    enter = slideInVertically(),
                    exit = slideOutVertically()
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
