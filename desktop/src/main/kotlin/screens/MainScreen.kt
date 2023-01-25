package screens

import AppController
import AppController.hideDialog
import AppController.hideWindow
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
import extensions.setMinimumSize
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import navigation.*
import screens.landing.LandingScreen
import utils.noRippleClickable

@OptIn(ExperimentalUnitApi::class)
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    applicationScope: ApplicationScope,
) {

    val preferences = UserPreferences()

    val isConnected by UserPreferences().hasNetworkConnection.collectAsState(true)
    val isLoading by UserPreferences().isLoading.collectAsState(initial = false)
    val myDialog by AppController.dialog.collectAsState(initial = null)
    val windowIsVisible by AppController.windowIsVisible.collectAsState(initial = true)

    val requiresSetup by preferences.signedIn.combine(preferences.isUserSetup) { signedIn, setup ->
        signedIn.not() or setup.not()
    }.collectAsState(initial = true)

    rememberCoroutineScope().launch {
        preferences.signedIn.collectLatest {
            println("-".repeat(10).plus("> SIGNED IN => ${it}"))
        }
        preferences.isUserSetup.collectLatest {
            println("-".repeat(10).plus("> SETUP => ${it}"))
        }

        println("-".repeat(10).plus("> REQUIRES => ${requiresSetup}"))
    }

    val title by remember { mutableStateOf(PoetreeApp().name()) }
    val state = rememberWindowState(
        position = WindowPosition.Aligned(Alignment.Center),
        placement = WindowPlacement.Floating,
        width = 980.dp,
        height = 720.dp,
    )

    val navController by rememberNavController(startDestination = NavigationItem.Home.route)

    applicationScope.Tray(
        tooltip = title,
        icon = painterResource("icons/logo_white.svg"),
        menu = {
            Item("Exit") { applicationScope.exitApplication() }
        }
    )

    Window(
        onCloseRequest = { hideWindow() },
        title = title,
        resizable = true,
        state = state,
        visible = windowIsVisible,
        icon = painterResource("icons/launcher.png")
    ) {

        setMinimumSize()

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

                    when{
                        requiresSetup -> LandingScreen()
                        else -> Navigation(navController = navController)
                    }.also {
                        println("-".repeat(10).plus("> VIEW IS UPDATED"))
                    }

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
                                    text = myDialog?.title ?: "",
                                    fontSize = TextUnit(24f, TextUnitType.Sp)
                                )
                                Text(
                                    modifier = Modifier.padding(16.dp).padding(vertical = 16.dp),
                                    text = myDialog?.description ?: "",
                                    textAlign = TextAlign.Center
                                )
                                TextButton(
                                    onClick = { hideDialog() }
                                ) {
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
