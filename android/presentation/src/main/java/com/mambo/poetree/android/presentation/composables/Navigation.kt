package com.mambo.poetree.android.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mambo.poetree.android.authentication.AuthenticationScreen
import com.mambo.poetree.android.landing.LandingScreen
import com.mambo.poetree.features.getstarted.GetStartedScreen
import com.mambo.poetree.helpers.MobileScreen

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@Composable
fun Navigation(
    isLoggedIn: Boolean,
    isOnBoarded: Boolean,
    isSetup: Boolean
) {

    val startDestination = when {
        isOnBoarded.not() -> MobileScreen.GetStarted.route
        isLoggedIn.not() -> MobileScreen.Landing.route
        isSetup.not() -> MobileScreen.Profile.route
        else -> MobileScreen.Feed.route
    }

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = MobileScreen.Authentication.route) {
        composable(route = MobileScreen.GetStarted.route) {
            GetStartedScreen(navController = navController)
        }
        composable(route = MobileScreen.Landing.route){
            LandingScreen(navController = navController)
        }
        composable(route = MobileScreen.Authentication.route){
            AuthenticationScreen(navController = navController)
        }
    }

}

@Preview
@Composable
fun NavigationPreview() {
    Navigation(isLoggedIn = false, isOnBoarded = false, isSetup = false)
}