package com.mambo.poetree.android.presentation.screens.home.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mambo.poetree.android.presentation.composables.TopBarIcon
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = viewModel()
) {
    SearchScreenContent(navigator, viewModel)
}

@Composable
fun SearchScreenContent(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                contentColor = MaterialTheme.colors.onSurface,
                backgroundColor = MaterialTheme.colors.surface,
                title = { Text(text = "Search") },
                navigationIcon = {
                    TopBarIcon(description = "navigate back") { navigator.popBackStack() }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.Tune, contentDescription = "filter")
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    tint = MaterialTheme.colors.onPrimary,
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = "scroll to start"
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {}
    }

}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(navigator = EmptyDestinationsNavigator)
}