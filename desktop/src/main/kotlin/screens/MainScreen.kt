package com.mambo.poetree.screens

import AppController
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.extensions.setMinimumSize
import com.mambo.poetree.navigation.Navigation
import com.mambo.poetree.navigation.NavigationItem
import com.mambo.poetree.navigation.rememberNavController
import utils.noRippleClickable

@OptIn(ExperimentalUnitApi::class)
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    applicationScope: ApplicationScope,
) {

    val isConnected by UserPreferences().hasNetworkConnection.collectAsState(true)
    val isLoading by AppController.isLoading.collectAsState(initial = false)
    val myDialog by AppController.dialog.collectAsState(initial = null)

    val signedIn by UserPreferences().signedIn.collectAsState(initial = false)
    val hasSetup by UserPreferences().isUserSetup.collectAsState(initial = false)

    val start = when{
        signedIn && hasSetup -> NavigationItem.Home.route
        else -> NavigationItem.Landing.route
    }

    val title by remember { mutableStateOf(PoetreeApp().name()) }
    val state = rememberWindowState(
        position = WindowPosition.Aligned(Alignment.Center),
        placement = WindowPlacement.Floating,
        width = 980.dp,
        height = 720.dp,
    )

    applicationScope.Tray(
        tooltip = title,
        icon = painterResource("icons/logo_white.svg"),
        menu = {
            Item("Exit") { applicationScope.exitApplication() }
        }
    )

    Window(
        onCloseRequest = { applicationScope.exitApplication() },
        title = title,
        resizable = true,
        state = state,
        icon = painterResource("icons/launcher.png")
    ) {

        setMinimumSize()

        val navigator by rememberNavController(startDestination = start)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier.fillMaxSize()) {

                    AnimatedVisibility(
                        visible = isConnected,
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

                    Navigation(navController = navigator)

                }

                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.25f))
                            .fillMaxSize()
                            .noRippleClickable { },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card {
                            CircularProgressIndicator(modifier = Modifier.padding(48.dp))
                        }
                    }
                }

                AnimatedVisibility(
                    visible = myDialog != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.25f))
                            .fillMaxSize()
                            .noRippleClickable { },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier.defaultMinSize(200.dp, 150.dp).fillMaxWidth(0.4f),
                            elevation = 4.dp
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 16.dp),
                                    text = myDialog?.first ?: "Title",
                                    fontSize = TextUnit(24f, TextUnitType.Sp)
                                )
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = myDialog?.second ?: "Message",
                                    textAlign = TextAlign.Center
                                )
                                TextButton(onClick = { AppController.hideDialog() }) {
                                    Text("dismiss")
                                }
                            }

                        }
                    }
                }

            }
        }
    }

}
