package com.mambo.poetree.android.poem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.darkrockstudios.richtexteditor.model.RichTextValue
import com.darkrockstudios.richtexteditor.model.Style
import com.darkrockstudios.richtexteditor.ui.RichTextEditor
import com.darkrockstudios.richtexteditor.ui.defaultRichTextFieldStyle
import com.mambo.poetree.android.ui.CommentField
import com.mambo.poetree.android.ui.composables.UserImage
import com.mambo.poetree.android.ui.composables.VerticalStat
import com.mambo.poetree.android.ui.navigation.navigateToComments
import com.mambo.poetree.android.ui.navigation.navigateToCompose
import com.mambo.poetree.android.ui.navigation.navigateToUser
import com.mambo.poetree.data.domain.Poem

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 19 Feb 2023
 */

@OptIn(ExperimentalUnitApi::class)
@Composable
private fun PoemScreenContent(
    navController: NavController, viewModel: PoemViewModel = viewModel()
) {

    val poem by viewModel.poem.collectAsState()
    val comment by viewModel.comment.collectAsState()
    val isEditable by viewModel.isEditable.collectAsState(initial = false)
    val isPublishable by viewModel.isPublishable.collectAsState(initial = false)

    var expanded by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface,
            title = {},
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack, contentDescription = "navigate back"
                    )
                }
            },
            actions = {
                if (isEditable)
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "menus")
                    }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            navController.navigateToCompose(poem = poem)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "edit poem"
                        )
                        Text(modifier = Modifier.padding(16.dp), text = "Edit")
                    }

                    if (isPublishable)
                        DropdownMenuItem(onClick = { expanded = false }) {
                            Icon(
                                imageVector = Icons.Outlined.Publish,
                                contentDescription = "publish poem"
                            )
                            Text(modifier = Modifier.padding(16.dp), text = "Publish")
                        }

                    if (isEditable)
                        DropdownMenuItem(onClick = { expanded = false }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "delete poem"
                            )
                            Text(modifier = Modifier.padding(16.dp), text = "Delete")
                        }
                }
            })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Row {
                    poem?.let {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f)
                                .verticalScroll(state = rememberScrollState())
                        ) {

                            val value = RichTextValue
                                .fromString(it.content)
                                .insertStyle(Style.Italic)

                            Text(
                                modifier = Modifier.padding(top = 16.dp),
                                text = "${it.displayTopic}  •  ${it.displayDate}  •  ${it.displayReads} reads"
                            )
                            Text(
                                modifier = Modifier.padding(vertical = 16.dp),
                                text = it.title,
                                fontSize = TextUnit(24f, TextUnitType.Sp),
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "By ${it.displayUser}")
                            RichTextEditor(
                                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                                readOnly = true,
                                value = value,
                                onValueChange = {},
                                textFieldStyle = defaultRichTextFieldStyle().copy(
                                    textColor = MaterialTheme.colors.onBackground,
                                    placeholderColor = MaterialTheme.colors.onBackground,
                                    textStyle = TextStyle(fontStyle = FontStyle.Italic)
                                )
                            )
                        }

                        if (it.type != Poem.Type.DRAFT) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                VerticalStat(
                                    icon = Icons.Rounded.ModeComment,
                                    count = poem?.comments ?: 0L
                                ) {
                                    navController.navigateToComments(poem = it)
                                }
                                VerticalStat(
                                    icon = Icons.Rounded.Bookmark,
                                    count = poem?.bookmarks ?: 0L
                                )
                                VerticalStat(
                                    icon = Icons.Rounded.Favorite,
                                    count = poem?.likes ?: 0L
                                )
                                if (isEditable.not())
                                    UserImage(
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .width(32.dp)
                                            .height(32.dp)
                                    ) {
                                        navController.navigateToUser(poem = it)
                                    }
                            }
                        }
                    }
                }

            }
            poem?.let {
                if (it.isDraft().not())
                    CommentField(
                        value = comment,
                        onValueChanged = { change -> viewModel.updateComment(change) },
                        isButtonEnabled = comment.isBlank().not(),
                    )
            }
        }
    }
}

@Composable
fun PoemScreen(navController: NavController) {
    PoemScreenContent(navController = navController)
}

@Preview
@Composable
fun PoemScreenPreview() {
    val context = LocalContext.current
    PoemScreen(navController = NavController(context = context))
}