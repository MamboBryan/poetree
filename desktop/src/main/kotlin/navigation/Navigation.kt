package com.mambo.poetree.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.mambo.poetree.screens.landing.LandingScreen
import screens.HomeScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavController) {
    NavHost(navController = navController) {
        composable(route = NavigationItem.Landing.route) {
            LandingScreen(navigator = navController)
        }
        composable(route = NavigationItem.Home.route) {
            HomeScreen(navigator = navController)
        }
    }.build()
}