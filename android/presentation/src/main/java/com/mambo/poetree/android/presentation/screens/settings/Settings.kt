package com.mambo.poetree.android.presentation.screens.settings

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
import androidx.compose.ui.tooling.preview.Preview
import com.mambo.poetree.AppMonitor.showDialog
import com.mambo.poetree.android.presentation.composables.TopBarIcon
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.utils.DialogData
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 12 Feb 2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(navigator: DestinationsNavigator) {


//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    TopBarIcon(description = "navigate back") { navigator.popBackStack() }
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
            showDialog(data = data)
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

@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator) {
    SettingsScreenContent(navigator = navigator)
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navigator = EmptyDestinationsNavigator)
}