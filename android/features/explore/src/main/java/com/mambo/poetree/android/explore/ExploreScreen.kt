package com.mambo.poetree.android.explore

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mambo.poetree.helpers.MobileScreen

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */

fun String.asColor() =  Color(android.graphics.Color.parseColor(this))

@Composable
fun ExploreScreenContent(
    navController: NavController,
    viewModel: ExploreViewModel = viewModel()
) {

    Scaffold(topBar = {
        TopAppBar(
            contentColor = MaterialTheme.colorScheme.onSurface,
            backgroundColor = MaterialTheme.colorScheme.surface,
            title = { Text(text = "Explore") },
            actions = {
                IconButton(onClick = { navController.navigate(MobileScreen.Explore.route) }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "search")
                }
                IconButton(onClick = { navController.navigate(MobileScreen.Explore.route) }) {
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

@Composable
fun ExploreScreen(navController: NavController ) {
    ExploreScreenContent(navController = navController)
}

@Preview
@Composable
fun ExploreScreenPreview() {
    val context = LocalContext.current
    ExploreScreen(navController = NavController(context = context))
}