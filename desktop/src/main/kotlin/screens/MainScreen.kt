package com.mambo.poetree.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.navigation.Navigation
import com.mambo.poetree.navigation.NavigationItem
import com.mambo.poetree.navigation.rememberNavController

@ExperimentalMaterialApi
@Composable
fun MainScreen(
    applicationScope: ApplicationScope,
) {

    // TODO: update connection status
    var isConnected by remember { mutableStateOf(true) }

    Window(
        onCloseRequest = { applicationScope.exitApplication() },
        title = PoetreeApp().name(),
//        icon = appIcon,
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            width = Dp.Unspecified,
            height = Dp.Unspecified,
        )
    ) {

        val navController by rememberNavController(startDestination = NavigationItem.Landing.route)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                AnimatedVisibility(
                    visible = isConnected,
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colors.error)
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.List,
                            contentDescription = "No Internet Connection",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp),
                            tint = MaterialTheme.colors.onError
                        )
                        Text(text = "No Internet Connection", color = MaterialTheme.colors.onError)
                    }
                }

                Navigation(navController = navController)

            }
        }
    }

}