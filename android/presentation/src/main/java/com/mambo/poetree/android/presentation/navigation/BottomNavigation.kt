package com.mambo.poetree.android.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryBooks
import androidx.compose.ui.graphics.vector.ImageVector
import com.mambo.poetree.android.presentation.screens.destinations.BookmarkScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.FeedScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.LibraryScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.SearchScreenDestination
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
    Search(direction = SearchScreenDestination, icon = Icons.Rounded.Explore, label = "Search"),
    Bookmark(direction = BookmarkScreenDestination, icon = Icons.Rounded.Bookmarks, label = "Bookmark"),
    Library(direction = LibraryScreenDestination, icon = Icons.Rounded.LibraryBooks, label = "Library")
}