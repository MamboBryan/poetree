package com.mambo.poetree.android.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.android.presentation.utils.AppMonitor
import com.mambo.poetree.android.presentation.utils.AppMonitor.hideLoading
import com.mambo.poetree.data.local.preferences.UserPreferences
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val preferences = UserPreferences()

    val hasNetworkConnection by mutableStateOf(preferences.hasNetworkConnection)

    val isSignedIn by mutableStateOf(preferences.signedIn)
    val isOnBoarded by mutableStateOf(preferences.isOnBoarded)
    val isSetup by mutableStateOf(preferences.isUserSetup)

    val isLoading by mutableStateOf(preferences.isLoading)
    val dialogFlow by mutableStateOf(AppMonitor.dialog)

    init {
        viewModelScope.launch {
           hideLoading()
            Timber.i("${"".repeat(10).plus("> USER \n")}${preferences.getUserData()}")
        }
    }

}