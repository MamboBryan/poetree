package com.mambo.poetree.android.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mambo.poetree.android.presentation.MainViewModel
import com.mambo.poetree.android.presentation.screens.destinations.AuthScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.HomeScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.OnBoardingScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(),
) {

    val hasInternetConnection by mainViewModel.hasNetworkConnection.collectAsState(initial = true)
    val isLoggedIn by mainViewModel.isSignedIn.collectAsState(initial = true)
    val isOnBoarded by mainViewModel.isOnBoarded.collectAsState(initial = true)

    val startRoute = when {
        isOnBoarded.not() -> OnBoardingScreenDestination
        isLoggedIn.not() -> AuthScreenDestination
        else -> HomeScreenDestination
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            AnimatedVisibility(
                visible = hasInternetConnection,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .fillMaxWidth()
                        .padding(all = 2.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CloudOff,
                        contentDescription = "No Internet Connection",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        tint = MaterialTheme.colors.onPrimary
                    )
                    Text(text = "No Internet Connection", color = MaterialTheme.colors.onPrimary)
                }
            }

            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = startRoute
            )

        }
    }

}