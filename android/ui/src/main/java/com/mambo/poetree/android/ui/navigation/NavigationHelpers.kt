package com.mambo.poetree.android.ui.navigation

import androidx.navigation.NavController
import com.mambo.poetree.data.domain.Poem

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 05 Mar 2023
 */

// POEM
fun NavController.navigateToPoem(poem: Poem) {
    val id = "id=${poem.id}"
    val type = "type=${poem.type.name}"
    val args = "?$id&$type"
    this.navigate(MobileScreen.Poem.route.plus(args))
}

// COMPOSE
fun NavController.navigateToCompose(poem: Poem? = null) {
    if (poem != null) {
        val id = "id=${poem.id}"
        val title = "type=${poem.type}"
        val topic = "topic=${poem.topic?.id ?: ""}"
        val route = MobileScreen.Compose.route
            .plus("?")
            .plus(id)
            .plus(title)
            .plus(topic)
        this.navigate(route) {
            popUpTo(MobileScreen.Poem.route) { inclusive = true }
        }
    } else {
        navigateToCompose()
    }
}

fun NavController.navigateToCompose() {
    this.navigate(MobileScreen.Compose.route)
}

// User
fun NavController.navigateToUser(poem: Poem? = null) {
    val userId = poem?.user?.id
    if (userId != null)
        navigateToUser(userId = userId)
    else
        navigateToUser()
}

private fun NavController.navigateToUser() {
    this.navigate(MobileScreen.User.route)
}

fun NavController.navigateToUser(userId: String) {
    this.navigate(MobileScreen.User.route.plus("?id=$userId"))
}

// COMMENTS
fun NavController.navigateToComments(poem: Poem) {
    val id = "id=${poem.id}"
    val title = "title=${poem.title}"
    val count = "count=${poem.comments}"
    val route = MobileScreen.Comments.route
        .plus("?")
        .plus(id)
        .plus("&")
        .plus(title)
        .plus("&")
        .plus(count)
    this.navigate(route)
}