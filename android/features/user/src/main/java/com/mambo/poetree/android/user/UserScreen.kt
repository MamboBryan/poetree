package com.mambo.poetree.android.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mambo.poetree.android.ui.composables.UserImage
import com.mambo.poetree.android.ui.composables.getUi
import com.mambo.poetree.android.ui.navigation.navigateToPoem

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 11 Mar 2023
 */

@OptIn(ExperimentalUnitApi::class)
@Composable
private fun UserScreenContent(
    navController: NavController,
    viewModel: UserViewModel = viewModel()
) {

    val user by viewModel.details.collectAsState()
    val poems by viewModel.poems.collectAsState()

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                elevation = 5.dp,
            ) {
                Column {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                    }
                    if (user != null) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            UserImage(
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .width(150.dp)
                                    .height(150.dp)
                            )
                            Text(
                                text = user?.name ?: "Username",
                                fontSize = TextUnit(24f, TextUnitType.Sp)
                            )
                            Text(
                                modifier = Modifier
                                    .width(300.dp)
                                    .padding(top = 8.dp)
                                    .align(Alignment.CenterHorizontally),
                                text = user?.bio ?: "bio",
                                textAlign = TextAlign.Center,
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                Detail(
                                    modifier = Modifier.weight(1f),
                                    title = "Reads",
                                    count = user?.reads ?: 0
                                )
                                Detail(
                                    modifier = Modifier.weight(1f),
                                    title = "Bookmarks",
                                    count = user?.bookmarks ?: 0
                                )
                                Detail(
                                    modifier = Modifier.weight(1f),
                                    title = "Likes",
                                    count = user?.likes ?: 0
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(items = poems) { poem ->
                    poem.getUi {
                        navController.navigateToPoem(poem = poem)
                    }
                }
            }
        }
    }
}

@Composable
fun UserScreen(navController: NavController) {
    UserScreenContent(navController = navController)
}


@Preview
@Composable
private fun UserScreenPreview() {
    val ctx = LocalContext.current
    UserScreen(navController = NavController(ctx))
}