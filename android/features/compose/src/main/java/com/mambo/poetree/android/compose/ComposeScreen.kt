package com.mambo.poetree.android.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.darkrockstudios.richtexteditor.ui.RichTextEditor
import com.darkrockstudios.richtexteditor.ui.defaultRichTextFieldStyle
import com.mambo.poetree.AppMonitor.showDialog
import com.mambo.poetree.android.ui.*
import com.mambo.poetree.utils.DialogData
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ComposeScreenContent(
    navController: NavController,
    viewModel: ComposeViewModel = viewModel()
) {

    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = BringIntoViewRequester()

    var topicQuery by remember { mutableStateOf("") }

    val sheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val showSheet = { scope.launch { sheetState.show() } }
    val hideSheet = { scope.launch { sheetState.hide() } }

    BottomSheet(state = sheetState, sheetContent = {

        Scaffold(topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.surface)
            ) {

                ClearTextField(
                    modifier = Modifier.weight(1f),
                    value = topicQuery,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        IconButton(onClick = { hideSheet() }) {
                            Icon(
                                imageVector = if (topicQuery.isBlank()) Icons.Rounded.Close else Icons.Rounded.ArrowBack,
                                contentDescription = "close sheet"
                            )
                        }
                    },
                    trailingIcon = {
                        if (topicQuery.isNotBlank()) IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "search topic"
                            )
                        }
                    },
                    onValueChanged = { query -> topicQuery = query })
            }
        }) {
            LazyVerticalGrid(
                modifier = Modifier.padding(it),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp)
            ) {

                this.items(
                    items = viewModel.topics.filter { item -> item.name.contains(topicQuery) }
                ) { topic ->

                    val isSelected = topic == viewModel.topic

                    val (background, foreground) = when (isSelected) {
                        true -> Pair(MaterialTheme.colors.primary, MaterialTheme.colors.onPrimary)
                        false -> Pair(MaterialTheme.colors.surface, MaterialTheme.colors.onSurface)
                    }

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                viewModel.updateTopic(topic = topic)
                                hideSheet()
                            },
                        backgroundColor = background
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = topic.name.replaceFirstChar { c -> c.uppercase() },
                            color = foreground
                        )
                    }

                }

            }
        }

    }) {
        Scaffold(
            topBar = {
                TopAppBar(backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.onSurface,
                    title = { Text(text = "Compose") },
                    navigationIcon = {
                        TopBarIcon(description = "navigate back") { navController.popBackStack() }
                    },
                    actions = {

                        AnimatedVisibility(visible = viewModel.poem != null) {
                            IconButton(onClick = {
                                showDialog(
                                    data = DialogData(title = "Deleting Poem",
                                        description = "Are you sure you want to delete this poem?",
                                        positiveText = "yes",
                                        negativeText = "no",
                                        positiveAction = {
                                            viewModel.delete {
                                                navController.popBackStack()
                                            }
                                        })
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = "delete poem"
                                )
                            }
                        }

                        AnimatedVisibility(visible = viewModel.isValidPoem) {

                            IconButton(onClick = {
                                showDialog(
                                    data = DialogData(title = "Publish Poem",
                                        description = "Are you sure you want to publish this poem?",
                                        positiveText = "yes",
                                        negativeText = "no",
                                        positiveAction = {
                                            viewModel.publish {

                                            }
                                        }
                                    )
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Publish,
                                    contentDescription = "publish poem"
                                )
                            }
                        }

                    })
            },
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                CompositionLocalProvider(
                    LocalRippleTheme provides if (viewModel.isButtonEnabled) LocalRippleTheme.current else NoRippleTheme
                ) {
                    FloatingActionButton(
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            if (viewModel.isButtonEnabled)
                                viewModel.save { navController.popBackStack() }
                        },
                        backgroundColor = if (viewModel.isButtonEnabled) MaterialTheme.colors.secondary else Color.Gray
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Save, contentDescription = "publish poem"
                        )
                    }
                }
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                CompositionLocalProvider(LocalTextToolbar provides EmptyTextTooltip) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (viewModel.topic == null) MaterialTheme.colors.background else viewModel.topic!!.color.asColor())
                            .clickable { showSheet() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = (viewModel.topic?.name
                                ?: "Topic").replaceFirstChar { it.uppercase() }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier.padding(end = 16.dp),
                            imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = "open topics"
                        )
                    }

                    Divider(modifier = Modifier.fillMaxWidth())

                    ClearTextField(value = viewModel.title,
                        maxLines = 2,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        ),
                        hint = {
                            Text(
                                color = MaterialTheme.colors.primary, text = "Title"
                            )
                        },
                        placeholder = {
                            Text(text = "Title...")
                        },
                        onValueChanged = { s -> viewModel.updateTitle(text = s) })

                    Divider(modifier = Modifier.fillMaxWidth())

                    AnimatedVisibility(visible = viewModel.content.value.text.isNotBlank()) {
                        Text(
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                            color = MaterialTheme.colors.primary,
                            text = "Content"
                        )
                    }

                    RichTextEditor(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .padding(top = if (viewModel.content.value.text.isNotBlank()) 8.dp else 16.dp)
                        .weight(1f)
                        .onFocusEvent { event ->
                            if (event.isFocused) {
                                scope.launch { bringIntoViewRequester.bringIntoView() }
                            }
                        }, value = viewModel.content, onValueChange = { richText ->
                        viewModel.updateContent(value = richText)
                    }, textFieldStyle = defaultRichTextFieldStyle().copy(
                        placeholder = "Art goes here...",
                        textColor = MaterialTheme.colors.onBackground,
                        placeholderColor = MaterialTheme.colors.onBackground,
                    )
                    )
                }
            }
        }
    }

}

@Composable
fun ComposeScreen(navController: NavController) {
    ComposeScreenContent(navController = navController)
}


@Preview
@Composable
fun ComposeScreenPreview() {
    val context = LocalContext.current
    ComposeScreen(navController = NavController(context = context))
}

