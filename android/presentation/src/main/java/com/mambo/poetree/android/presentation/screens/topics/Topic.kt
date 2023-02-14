package com.mambo.poetree.android.presentation.screens.topics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoFixHigh
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mambo.poetree.android.presentation.composables.TopBarIcon
import com.mambo.poetree.android.presentation.composables.TopicLarge
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

@Composable
fun ColorItem(
    modifier: Modifier = Modifier,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .size(width = 50.dp, height = 50.dp)
            .clickable { onClick.invoke() },
        backgroundColor = color,
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colors.onBackground) else null,
        elevation = if (isSelected) 3.dp else 0.dp,
        shape = RoundedCornerShape(10.dp)
    ) {

    }
}

@Destination
@Composable
fun TopicScreen(
    navigator: DestinationsNavigator,
    viewModel: TopicViewModel = viewModel()
) {
    TopicScreenContent(navigator = navigator, viewModel = viewModel)
}

@Composable
fun TopicScreenContent(navigator: DestinationsNavigator, viewModel: TopicViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                navigationIcon = {
                    TopBarIcon(
                        description = "navigate back"
                    ) {
                        navigator.popBackStack()
                    }
                },
                title = { Text(text = "Topic") },
                actions = {
                    AnimatedVisibility(visible = viewModel.topic != null) {
                        TopBarIcon(
                            imageVector = Icons.Rounded.Delete,
                            description = "Delete topic"
                        ) {
                            viewModel.delete { navigator.popBackStack() }
                        }
                    }
                })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            TopicLarge(
                modifier = Modifier.fillMaxWidth(),
                name = viewModel.name.ifBlank { "Topic" },
                color = viewModel.color
            )

            LazyVerticalGrid(
                modifier = Modifier.padding(top = 16.dp),
                columns = GridCells.Fixed(5)
            ) {
                items(items = viewModel.colors) { color ->
                    ColorItem(
                        modifier = Modifier.padding(8.dp),
                        color = color,
                        isSelected = color == viewModel.color
                    ) {
                        viewModel.updateColor(selected = color)
                    }
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = viewModel.name,
                onValueChange = { name ->
                    viewModel.updateName(str = name)
                },
                placeholder = { Text(text = "Shy") },
                label = { Text(text = "Name") }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = {
                    viewModel.save { navigator.popBackStack() }
                },
                enabled = viewModel.name.isNotBlank() and viewModel.name.contains(" ").not()
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = (if (viewModel.topic == null) "create" else "update").uppercase()
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { viewModel.generateRandomColors() }) {
                    Icon(
                        imageVector = Icons.Rounded.AutoFixHigh,
                        contentDescription = "generate colors"
                    )
                }
            }

        }
    }
}


@Preview
@Composable
fun TopicScreenPreview() {
    TopicScreen(navigator = EmptyDestinationsNavigator)
}