package com.mambo.poetree.android.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.mambo.poetree.AppMonitor
import com.mambo.poetree.android.ui.TopBarIcon
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.utils.DialogData

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 19 Feb 2023
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(navController: NavController) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    TopBarIcon(description = "navigate back") { navController.popBackStack() }
                },
                actions = { },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colors.surface,
                    scrolledContainerColor = MaterialTheme.colors.surface,
                    navigationIconContentColor = MaterialTheme.colors.onSurface,
                    titleContentColor = MaterialTheme.colors.onSurface,
                    actionIconContentColor = MaterialTheme.colors.onSurface,
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        fun signOut() {
            val data = DialogData(
                title = "Oh No!",
                description = "We'll miss you and by signing out you'll lose all your saved poems. Are you sure you want to sign out?",
                positiveAction = { UserPreferences().signedOut() },
                negativeAction = {}
            )
            AppMonitor.showDialog(data = data)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(modifier = Modifier.padding(padding)) {
                Button(onClick = { signOut() }) {
                    Text(text = "sign out")
                }
            }
        }

    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    SettingsScreenContent(navController = navController)
}

@Preview
@Composable
fun SettingsScreenPreview() {
    val context = LocalContext.current
    SettingsScreen(navController = NavController(context = context))
}