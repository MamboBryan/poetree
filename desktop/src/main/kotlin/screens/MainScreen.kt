package com.mambo.poetree.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.extensions.setMinimumSize
import com.mambo.poetree.navigation.Navigation
import com.mambo.poetree.navigation.NavigationItem
import com.mambo.poetree.navigation.rememberNavController

@ExperimentalMaterialApi
@Composable
fun MainScreen(
    applicationScope: ApplicationScope,
) {

    // TODO: update connection status
    val isConnected by remember { mutableStateOf(true) }
    val title by remember { mutableStateOf(PoetreeApp().name()) }
    val state = rememberWindowState(
        position = WindowPosition.Aligned(Alignment.Center),
        placement = WindowPlacement.Floating,
        width = 980.dp,
        height = 720.dp,
    )

    applicationScope.Tray(
        tooltip = title,
        icon = painterResource("icons/logo.svg"),
        menu = {
            Item("Exit") { applicationScope.exitApplication() }
        }
    )

    Window(
        onCloseRequest = { applicationScope.exitApplication() },
        title = title,
        resizable = true,
        state = state,
        icon = painterResource("icons/logo.png")
    ) {

        setMinimumSize()

        val navController by rememberNavController(startDestination = NavigationItem.Landing.route)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                AnimatedVisibility(
                    visible = isConnected.not(),
                    enter = slideInVertically(),
                    exit = slideOutVertically()
                ) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colors.primary)
                            .fillMaxWidth()
                            .padding(all = 4.dp),
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
                        Text(
                            text = "No Internet Connection",
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }

                Navigation(navController = navController)

            }
        }
    }

}
