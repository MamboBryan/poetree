package com.mambo.poetree.android.presentation.screens.home.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SearchScreenContent(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Explore", fontSize = TextUnit(24f, TextUnitType.Sp))
            Row {
                Image(
                    modifier = Modifier
                        .clickable {
                            Toast
                                .makeText(context, "Searching", Toast.LENGTH_LONG)
                                .show()
                        }
                        .padding(start = 8.dp),
                    imageVector = Icons.Rounded.Search, contentDescription = "Search Poem"
                )
                Image(
                    modifier = Modifier
                        .clickable {
                            Toast
                                .makeText(context, "Creating", Toast.LENGTH_LONG)
                                .show()
                        }
                        .padding(start = 8.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Create Topic"
                )
            }

        }
    }
}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(navigator = EmptyDestinationsNavigator)
}