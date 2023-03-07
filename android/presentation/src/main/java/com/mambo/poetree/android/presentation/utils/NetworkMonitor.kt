package com.mambo.poetree.android.presentation.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.mambo.poetree.data.local.preferences.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue 07 Mar 2023
 */
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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    private fun checkNetworkState() {
        val isConnected = isNetworkAvailable()
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.updateNetworkConnection(isConnected)
        }
    }

    fun register() {
        manager.registerNetworkCallback(request, this)
    }

}