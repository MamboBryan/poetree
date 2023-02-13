package com.mambo.poetree.android.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mambo.poetree.android.presentation.navigation.BottomNavigation
import com.mambo.poetree.android.presentation.screens.NavGraph
import com.mambo.poetree.android.presentation.screens.destinations.*
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo


object HomeNavigation {
    val graph = NavGraph(
        route = "home", startRoute = FeedScreenDestination, destinations = listOf(
            FeedScreenDestination,
            SearchScreenDestination,
            BookmarkScreenDestination,
            LibraryScreenDestination,
            ComposeScreenDestination,
            PoemScreenDestination,
            CommentScreenDestination,
            ProfileScreenDestination,
            SettingsScreenDestination
        )
    )
}

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }) {
        Column(modifier = Modifier.padding(it)) {
            DestinationsNavHost(navController = navController, navGraph = HomeNavigation.graph)
        }
    }

}

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        ?: FeedScreenDestination.route

    val bottomNavigationIsVisible = when (currentDestination) {
        FeedScreenDestination.route,
        SearchScreenDestination.route,
        BookmarkScreenDestination.route,
        LibraryScreenDestination.route -> true
        else -> false
    }


    if (bottomNavigationIsVisible)
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary
        ) {
            BottomNavigation.values().forEach { destination ->
                BottomNavigationItem(
                    selected = currentDestination == destination.direction.route,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigateTo(destination.direction) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(destination.icon, contentDescription = destination.label)
                    },
                )
            }
        }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navigator = EmptyDestinationsNavigator)
}