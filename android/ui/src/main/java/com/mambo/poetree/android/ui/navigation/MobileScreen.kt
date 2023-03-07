package com.mambo.poetree.android.ui.navigation

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
sealed class MobileScreen(val route: String) {
    object GetStarted : MobileScreen(route = "on_boarding")
    object Landing : MobileScreen(route = "landing")
    object Authentication : MobileScreen(route = "authentication")
    object Account : MobileScreen(route = "setup")
    object Feed : MobileScreen(route = "feed")
    object Explore : MobileScreen(route = "explore")
    object Bookmarks : MobileScreen(route = "bookmarks")
    object Library : MobileScreen(route = "library")
    object Compose : MobileScreen(route = "compose")
    object Settings : MobileScreen(route = "settings")
    object Poem : MobileScreen(route = "poem")
}