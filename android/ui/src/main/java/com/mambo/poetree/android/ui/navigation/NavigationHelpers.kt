package com.mambo.poetree.android.ui.navigation

import androidx.navigation.NavController
import com.mambo.poetree.data.domain.Poem

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 05 Mar 2023
 */
fun NavController.navigateToPoem(poem: Poem) {
    val id = "id=${poem.id}"
    val type = "type=${poem.type.name}"
    val args = "?$id&$type"
    this.navigate(MobileScreen.Poem.route.plus(args))
}
fun NavController.navigateToCompose(poemId: String, poemType: String, topicId: Int?) {
    val topic = topicId ?: ""
    this.navigate(MobileScreen.Compose.route.plus("?id=$poemId&type=$poemType&topic=$topic")){
        popUpTo(MobileScreen.Poem.route){ inclusive = false }
    }
}

fun NavController.navigateToCompose() {
    this.navigate(MobileScreen.Compose.route)
}