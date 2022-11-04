package com.mambo.poetree.android.presentation.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.*
import com.mambo.poetree.data.local.preferences.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetworkMonitor(
    private val context: Context,
    private val userPreferences: UserPreferences = UserPreferences()
) : ConnectivityManager.NetworkCallback() {

    private val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val request = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    init {
        checkNetworkState()
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.updateNetworkConnection(true)
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.updateNetworkConnection(false)
        }
    }

    private fun checkNetworkState() {
        try {
            val networkInfo: NetworkInfo? = manager.activeNetworkInfo
            val isConnected = networkInfo != null && networkInfo.isConnected
            CoroutineScope(Dispatchers.IO).launch {
                userPreferences.updateNetworkConnection(isConnected)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun register() {
        manager.registerNetworkCallback(request, this)
    }

}