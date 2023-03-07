package com.mambo.poetree.android.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mambo.poetree.android.ui.navigation.MobileScreen

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

enum class BottomNavigationRoutes(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    Feed(
        route = MobileScreen.Feed.route,
        icon = Icons.Rounded.Home,
        label = "Home"
    ),
    Explore(
        route = MobileScreen.Explore.route,
        icon = Icons.Rounded.Explore,
        label = "Explore"
    ),
    Bookmarks(
        route = MobileScreen.Bookmarks.route,
        icon = Icons.Rounded.Bookmarks,
        label = "Bookmark"
    ),
    Library(
        route = MobileScreen.Library.route,
        icon = Icons.Rounded.LibraryBooks,
        label = "Library"
    )
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
