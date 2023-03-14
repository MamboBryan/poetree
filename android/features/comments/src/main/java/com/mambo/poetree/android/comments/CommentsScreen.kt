@file:OptIn(ExperimentalUnitApi::class)

package com.mambo.poetree.android.comments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mambo.poetree.android.ui.CommentField
import com.mambo.poetree.utils.prettyCount

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 11 Mar 2023
 */

@Composable
private fun CommentsScreenContent(
    navController: NavController,
    viewModel: CommentsViewModel = viewModel()
) {

    val title by viewModel.title.collectAsState()
    val count by viewModel.count.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val comment by viewModel.comment.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = title ?: "Title",
                    fontSize = TextUnit(24f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
                Text(text = (count?.prettyCount() ?: "0").plus(" comments"))
            }
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                items(items = comments) {
                    Comment(navController = navController, comment = it) {

                    }
                }
            }
            CommentField(
                value = comment,
                onValueChanged = { change -> viewModel.updateComment(change) },
                isButtonEnabled = comment.isBlank().not(),
            ) {

            }
        }
    }

}

@Composable
fun CommentsScreen(navController: NavController) {
    CommentsScreenContent(navController = navController)
}

@Preview
@Composable
private fun CommentsScreenPreview() {
    val ctx = LocalContext.current
    CommentsScreen(navController = NavController(ctx))
}