package com.mambo.poetree.android.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mambo.poetree.android.authentication.AuthenticationScreen
import com.mambo.poetree.android.bookmarks.BookmarkScreen
import com.mambo.poetree.android.compose.ComposeScreen
import com.mambo.poetree.android.explore.ExploreScreen
import com.mambo.poetree.android.landing.LandingScreen
import com.mambo.poetree.android.library.LibraryScreen
import com.mambo.poetree.android.poem.PoemScreen
import com.mambo.poetree.android.settings.SettingsScreen
import com.mambo.poetree.android.ui.navigation.MobileScreen
import com.mambo.poetree.feature.account.AccountScreen
import com.mambo.poetree.feature.feed.FeedScreen
import com.mambo.poetree.features.getstarted.GetStartedScreen

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    isLoggedIn: Boolean,
    isOnBoarded: Boolean,
    isSetup: Boolean
) {
    val startDestination = when {
        isOnBoarded.not() -> MobileScreen.GetStarted.route
        isLoggedIn.not() -> MobileScreen.Landing.route
        isSetup.not() -> MobileScreen.Account.route
        else -> MobileScreen.Feed.route
    }
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = MobileScreen.GetStarted.route) {
                GetStartedScreen(navController = navController)
            }
            composable(route = MobileScreen.Landing.route) {
                LandingScreen(navController = navController)
            }
            composable(route = MobileScreen.Authentication.route) {
                AuthenticationScreen(navController = navController)
            }
            composable(route = MobileScreen.Account.route) {
                AccountScreen(navController = navController)
            }
            composable(route = MobileScreen.Feed.route) {
                FeedScreen(navController = navController)
            }
            composable(route = MobileScreen.Explore.route) {
                ExploreScreen(navController = navController)
            }
            composable(route = MobileScreen.Bookmarks.route) {
                BookmarkScreen(navController = navController)
            }
            composable(route = MobileScreen.Library.route) {
                LibraryScreen(navController = navController)
            }
            composable(route = MobileScreen.Settings.route) {
                SettingsScreen(navController = navController)
            }
            composable(
                route = MobileScreen.Compose.route.plus("?id={id}&type={type}&topic={topic}"),
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("type") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("topic") {
                        type = NavType.StringType
                        nullable = true
                    },
                )
            ) {
                ComposeScreen(navController = navController)
            }
            composable(
                route = MobileScreen.Poem.route.plus("?id={id}&type={type}"),
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("type") {
                        type = NavType.StringType
                        nullable = true
                    },
                )
            ) {
                PoemScreen(navController = navController)
            }
        }
    }
}

@Preview
@Composable
fun NavigationPreview() {
    Navigation(isLoggedIn = false, isOnBoarded = false, isSetup = false)
}