package com.mambo.poetree.android.presentation.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.android.presentation.extensions.slideInLeft
import com.mambo.poetree.android.presentation.extensions.slideInRight
import com.mambo.poetree.android.presentation.extensions.slideOutLeft
import com.mambo.poetree.android.presentation.extensions.slideOutRight
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

enum class Section {
    STARTED, AUTHENTICATION
}

@Destination
@Composable
fun AuthScreen(
    navController: DestinationsNavigator
) {

    var section by remember {
        mutableStateOf(Section.STARTED)
    }

    AnimatedVisibility(
        visible = section == Section.STARTED,
        enter = slideInLeft(),
        exit = slideOutLeft()
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
                    section = Section.AUTHENTICATION
                }) {
                Text(modifier = Modifier.padding(vertical = 4.dp), text = "Get Started")
            }

        }
    }

    AnimatedVisibility(
        visible = section == Section.AUTHENTICATION,
        enter = slideInRight(),
        exit = slideOutRight()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Auth")
        }
    }

}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(navController = EmptyDestinationsNavigator)
}