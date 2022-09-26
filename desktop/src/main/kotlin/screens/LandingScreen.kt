package com.mambo.poetree.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.data.repositories.PoetreeRepository
import com.mambo.poetree.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun LandingScreen(
    navController: NavController?
) {

    var response by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(response.isNotBlank()) }

    GlobalScope.launch {
        val res = PoetreeRepository().goofy()
        CoroutineScope(Dispatchers.Unconfined).launch {
            response = res.message
        }
    }

    AnimatedVisibility(modifier = Modifier.padding(16.dp), visible = response.isNotBlank()) {
        Snackbar {
            Text(text = response)
        }
    }

    Column(
        modifier = Modifier.padding(24.dp),
    ) {

        Text(text = PoetreeApp().name())

        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = PoetreeApp().dummyPoem(),
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = PoetreeApp().dummyPoet()
            )
        }

        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}) {
                Text(text = "Get Started")
            }
        }

    }
}

@Preview
@Composable
fun LandingScreenPreview() {

    LandingScreen(null)

}
