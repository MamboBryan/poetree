package com.mambo.poetree.android.presentation.screens.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mambo.poetree.android.presentation.composables.TopicLarge
import com.mambo.poetree.android.presentation.screens.destinations.SearchScreenDestination
import com.mambo.poetree.android.presentation.screens.destinations.TopicScreenDestination
import com.mambo.poetree.android.presentation.utils.asColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue 14 Feb 2023
 */

@Composable
fun ExploreScreenContent(navigator: DestinationsNavigator, viewModel: ExploreViewModel) {
    Scaffold(topBar = {
        TopAppBar(
            contentColor = MaterialTheme.colorScheme.onSurface,
            backgroundColor = MaterialTheme.colorScheme.surface,
            title = { Text(text = "Explore") },
            actions = {
                IconButton(onClick = { navigator.navigate(SearchScreenDestination) }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "search")
                }
                IconButton(onClick = { navigator.navigate(TopicScreenDestination) }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "create topic")
                }
            })
    }) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.padding(innerPadding),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = viewModel.topics) { topic ->
                TopicLarge(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { },
                    isInList = true,
                    name = topic.name,
                    color = topic.color.asColor()
                )
            }
        }
    }
}

@Destination
@Composable
fun ExploreScreen(
    navigator: DestinationsNavigator,
    exploreViewModel: ExploreViewModel = viewModel()
) {
    ExploreScreenContent(navigator = navigator, viewModel = exploreViewModel)
}

@Preview
@Composable
fun ExploreScreenPreview() {
    ExploreScreen(navigator = EmptyDestinationsNavigator)
}
