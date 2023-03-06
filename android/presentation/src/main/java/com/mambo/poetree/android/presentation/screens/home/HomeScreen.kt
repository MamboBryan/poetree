package com.mambo.poetree.android.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mambo.poetree.android.presentation.navigation.BottomNavigationRoutes
import com.mambo.poetree.android.presentation.screens.NavGraph
import com.mambo.poetree.android.presentation.screens.destinations.*
import com.mambo.poetree.helpers.MobileScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


object HomeNavigation {
    val graph = NavGraph(
        route = "home", startRoute = FeedScreenDestination, destinations = listOf(
            FeedScreenDestination,
            ExploreScreenDestination,
            SearchScreenDestination,
            BookmarkScreenDestination,
            LibraryScreenDestination,
            ComposeScreenDestination,
            PoemScreenDestination,
            CommentScreenDestination,
            ProfileScreenDestination,
            SettingsScreenDestination,
            TopicScreenDestination
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val bottomNavigationIsVisible = when (currentDestination) {
        MobileScreen.Feed.route,
        MobileScreen.Explore.route,
        MobileScreen.Bookmarks.route,
        MobileScreen.Library.route -> true
        else -> false
    }

    if (bottomNavigationIsVisible)
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary
        ) {
            BottomNavigationRoutes.values().forEach { screen ->
                BottomNavigationItem(
                    selected = currentDestination == screen.route,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(screen.icon, contentDescription = screen.label)
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