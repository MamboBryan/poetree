package com.mambo.poetree.android.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mambo.poetree.android.ui.TopBarIcon

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 11 Mar 2023
 */

@Composable
fun UserScreenContent(
    navController: NavController,
    viewModel: UserViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopBarIcon(description = "navigate back") {
                navController.popBackStack()
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}

@Composable
fun UserScreen(navController: NavController) {
    UserScreenContent(navController = navController)
}


@Preview
@Composable
fun UserScreenPreview() {
    val ctx = LocalContext.current
    UserScreen(navController = NavController(ctx))
}