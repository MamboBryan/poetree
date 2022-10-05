package com.mambo.poetree.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
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

    Row {

        Column(
            modifier = Modifier.padding(8.dp)
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(MaterialTheme.shapes.medium.topStart))
                .background(color = MaterialTheme.colors.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Landing",
                color = MaterialTheme.colors.onPrimary
            )
        }

        Column(
            modifier = Modifier.padding(24.dp).weight(1f),
        ) {

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = PoetreeApp().name(),
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = PoetreeApp().dummyPoem(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = PoetreeApp().dummyPoet(),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                }) {
                Text(modifier = Modifier.padding(vertical = 4.dp), text = "Get Started")
            }

        }

    }
}

@Preview
@Composable
fun LandingScreenPreview() {

    LandingScreen(null)

}
