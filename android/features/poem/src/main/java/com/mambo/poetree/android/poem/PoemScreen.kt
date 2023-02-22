package com.mambo.poetree.android.poem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 19 Feb 2023
 */

@Composable
private fun PoemScreenContent(
    navController: NavController,
    viewModel: PoemViewModel = viewModel()
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Poem")
        }
    }
}

@Composable
fun PoemScreen(navController: NavController) {
    PoemScreenContent(navController = navController)
}

@Preview
@Composable
fun PoemScreenPreview() {
    val context = LocalContext.current
    PoemScreen(navController = NavController(context = context))
}