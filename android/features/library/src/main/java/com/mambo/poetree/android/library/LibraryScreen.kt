package com.mambo.poetree.android.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mambo.poetree.android.ui.navigation.navigateToPoem

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

@Composable
fun LibraryScreen(navController: NavController) {
    LibraryScreenContent(navController = navController)
}

@Composable
fun LibraryScreenContent(
    navController: NavController,
    viewModel: LibraryViewModel = viewModel()
) {
    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
            title = { Text(text = "Library") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Search, "search library")
                }
            })
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(items = viewModel.drafts) { poem ->
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigateToPoem(poem = poem)
                        },
                    colors = CardDefaults.cardColors()
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = poem.title.ifBlank { "Title" }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LibraryScreenPreview() {
    val context = LocalContext.current
    LibraryScreen(navController = NavController(context = context))
}