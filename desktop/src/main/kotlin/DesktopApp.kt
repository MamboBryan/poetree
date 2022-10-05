package com.mambo.poetree

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.application
import com.mambo.poetree.screens.MainScreen
import com.mambo.poetree.theme.PoetreeTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun DesktopApp(
    applicationScope: ApplicationScope
) {
    PoetreeTheme {
        MainScreen(applicationScope = applicationScope)
    }
}

fun main() = application {
    DesktopApp(this)
}