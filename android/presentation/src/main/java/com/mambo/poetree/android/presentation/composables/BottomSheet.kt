package com.mambo.poetree.android.presentation.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Wed 15 Feb 2023
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
    state: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()

    BackHandler(state.isVisible) { coroutineScope.launch { state.hide() } }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = shape,
        sheetContent = {
            sheetContent.invoke()
        }
    ) {
        content.invoke()
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun BottomSheetPreview() {

    val sheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    BottomSheet(state = sheetState, sheetContent = {}) {

    }
}