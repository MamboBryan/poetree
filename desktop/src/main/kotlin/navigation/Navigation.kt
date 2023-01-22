package navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import screens.HomeScreen
import screens.landing.LandingScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(navController: NavController) {
    println("-".repeat(10).plus("> NAVIGATION => ${navController.currentDestination}"))
    NavHost(navController = navController) {
        composable(route = NavigationItem.Landing.route) { LandingScreen() }
        composable(route = NavigationItem.Home.route) { HomeScreen(navigator = navController) }
    }.build()
}