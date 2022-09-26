package com.mambo.poetree.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mambo.poetree.android.presentation.screens.LandingScreen
import com.mambo.poetree.android.presentation.screens.MainScreen
import com.mambo.poetree.android.presentation.screens.NavGraphs
import com.mambo.poetree.android.presentation.theme.PoetreeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        viewModel.getGoofyMessage()

        setContent {
            PoetreeTheme {
                MainScreen {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    PoetreeTheme {
        MainScreen {
            LandingScreen(navController = EmptyDestinationsNavigator)
        }
    }
}