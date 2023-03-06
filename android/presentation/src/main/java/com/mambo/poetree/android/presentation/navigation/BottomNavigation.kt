package com.mambo.poetree.android.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.ui.graphics.vector.ImageVector
import com.mambo.poetree.android.presentation.screens.destinations.BookmarkScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.ExploreScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.FeedScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.LibraryScreenDestination
import com.mambo.poetree.helpers.MobileScreen
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */
enum class BottomNavigation(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: String
) {
    Feed(direction = FeedScreenDestination, icon = Icons.Rounded.Home, label = "Home"),
    Explore(direction = ExploreScreenDestination, icon = Icons.Rounded.Explore, label = "Explore"),
    Bookmark(direction = BookmarkScreenDestination, icon = Icons.Rounded.Bookmarks, label = "Bookmark"),
    Library(direction = LibraryScreenDestination, icon = Icons.Rounded.LibraryBooks, label = "Library")
}

enum class BottomNavigationRoutes(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    Feed(route = MobileScreen.Feed.route, icon = Icons.Rounded.Home, label = "Home"),
    Explore(route = MobileScreen.Explore.route, icon = Icons.Rounded.Explore, label = "Explore"),
    Bookmarks(route = MobileScreen.Bookmarks.route, icon = Icons.Rounded.Bookmarks, label = "Bookmark"),
    Library(route = MobileScreen.Library.route, icon = Icons.Rounded.LibraryBooks, label = "Library")
}