package com.mambo.poetree.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.mambo.poetree.screens.LandingScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavController) {

    NavHost(navController = navController) {
        composable(route = NavigationItem.Landing.route) {
            LandingScreen(navController = navController)
        }

    }.build()
}