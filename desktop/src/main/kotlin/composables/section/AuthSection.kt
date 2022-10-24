package com.mambo.poetree.composables.section

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mambo.poetree.navigation.NavController

@Composable
fun AuthSection(navController: NavController, modifier: Modifier = Modifier) {

    Column {
        Text("Auth")
    }

}

@Preview
@Composable
fun AuthSectionPreview() {
    AuthSection(navController = NavController(""))
}