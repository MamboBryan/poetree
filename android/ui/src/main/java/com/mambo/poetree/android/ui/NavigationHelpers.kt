package com.mambo.poetree.android.ui

import androidx.navigation.NavController
import com.mambo.poetree.helpers.MobileScreen

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 05 Mar 2023
 */
fun NavController.navigateToCompose(poemId: String, poemType: String, topicId: Int?) {
    this.navigate(MobileScreen.Compose.route.plus("?id=$poemId&type=$poemType&topic=$topicId")){
        popUpTo(MobileScreen.Poem.route){ inclusive = false }
    }
}

fun NavController.navigateToCompose() {
    this.navigate(MobileScreen.Compose.route)
}