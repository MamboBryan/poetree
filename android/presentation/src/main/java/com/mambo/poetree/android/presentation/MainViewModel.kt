package com.mambo.poetree.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.repositories.PoetreeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val preferences = UserPreferences()

    val isSignedIn = preferences.signedIn
    val hasNetworkConnection = preferences.hasNetworkConnection

    init {
        loopConnection()
    }

    fun getGoofyMessage() = viewModelScope.launch {
        val response = PoetreeRepository().goofy()
        Timber.i("RESPONSE -> $response")
    }

    private fun loopConnection(){
        viewModelScope.launch {
            delay(5000)
            val isConnected = preferences.hasNetworkConnection.firstOrNull() ?: true
            preferences.updateNetworkConnection(isConnected.not())
            delay(8000)
            loopConnection()
        }
    }

}