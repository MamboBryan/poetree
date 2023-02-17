package com.mambo.poetree.android.presentation.screens.home.libray

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

@Destination
@Composable
fun LibraryScreen() {
    LibraryScreenContent()
}

@Composable
fun LibraryScreenContent(
    viewModel: LibraryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Library") },
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
                        .fillMaxWidth(),
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
    LibraryScreen()
}