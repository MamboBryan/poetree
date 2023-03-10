package com.mambo.poetree.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.android.ui.composables.UserImage
import com.mambo.poetree.android.ui.composables.getUi
import com.mambo.poetree.android.ui.navigation.MobileScreen
import com.mambo.poetree.android.ui.navigation.navigateToPoem
import com.mambo.poetree.data.domain.Poem

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@Composable
fun FeedScreen(
    navController: NavController
) {
    val navigateToProfile = { navController.navigate(MobileScreen.Account.route) }
    val navigateToSettings = { navController.navigate(MobileScreen.Settings.route) }
    val navigateToPoem = { poem: Poem -> navController.navigateToPoem(poem = poem) }
    val navigateToCompose = { navController.navigate(MobileScreen.Compose.route) }

    FeedScreenContent(navigateToProfile = navigateToProfile,
        navigateToSettings = navigateToSettings,
        navigateToPoem = navigateToPoem,
        navigateToCompose = { navigateToCompose.invoke() })
}


@Composable
fun FeedScreenContent(
    navigateToProfile: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToPoem: (Poem) -> Unit,
    navigateToCompose: () -> Unit,
    viewModel: FeedViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val poems by viewModel.poems.collectAsState()

    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            UserImage(onClick = navigateToProfile)
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                Text(text = PoetreeApp().name())
            }
            IconButton(
                onClick = navigateToSettings
            ) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "settings")
            }
        }
    }, floatingActionButton = {
        FloatingActionButton(
            shape = RoundedCornerShape(25),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            onClick = { navigateToCompose() }
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "create poem")
        }
    }) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(items = poems) { poem ->
                    poem.getUi { navigateToPoem(poem) }
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedScreenPreview() {
    val context = LocalContext.current
    FeedScreen(navController = NavController(context = context))
}