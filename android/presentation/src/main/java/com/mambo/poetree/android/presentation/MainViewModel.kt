package com.mambo.poetree.android.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mambo.poetree.AppMonitor
import com.mambo.poetree.AppMonitor.hideLoading
import com.mambo.poetree.data.local.preferences.UserPreferences

class MainViewModel : ViewModel() {

    private val preferences = UserPreferences()

    val hasNetworkConnection by mutableStateOf(preferences.hasNetworkConnection)

    val isSignedIn by mutableStateOf(preferences.signedIn)
    val isOnBoarded by mutableStateOf(preferences.isOnBoarded)
    val isSetup by mutableStateOf(preferences.isUserSetup)

    val isLoading by mutableStateOf(preferences.isLoading)
    val dialogFlow by mutableStateOf(AppMonitor.dialog)

    init {
        hideLoading()
    }

}