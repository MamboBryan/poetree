package com.mambo.poetree.data.local.preferences

import com.mambo.poetree.data.domain.User
import com.mambo.poetree.utils.DialogData
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getBooleanFlow
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.russhwolf.settings.get
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@OptIn(ExperimentalSettingsApi::class, ExperimentalCoroutinesApi::class)
class UserPreferences {

    private val settings = Settings() as ObservableSettings

    private object PreferencesKeys {
        const val DARK_MODE = "dark_mode"
        const val IS_ON_BOARDED = "is_app_opened"
        const val IS_SIGNED_IN = "is_logged_in"
        const val IS_SETUP = "is_set_up"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_DETAILS = "user_details"
        const val IS_SIGNED_OUT = "logged_out"
        const val IMAGE_URL = "image_url"
        const val HAS_NETWORK_CONNECTION = "has_network_connection"
        const val IS_LOADING = "is_loading"
        const val DIALOG_DATA = "dialog_data"
    }

    val hasNetworkConnection = settings.getBooleanFlow(PreferencesKeys.HAS_NETWORK_CONNECTION, true)
    val darkMode = settings.getBooleanFlow(PreferencesKeys.DARK_MODE, false)
    val isLoading = settings.getBooleanFlow(PreferencesKeys.IS_LOADING, false)

    val isOnBoarded = settings.getBooleanFlow(PreferencesKeys.IS_ON_BOARDED, false)

    val signedIn = settings.getBooleanFlow(PreferencesKeys.IS_SIGNED_IN, false)
    val isUserSetup = settings.getBooleanFlow(PreferencesKeys.IS_SETUP, false)

    val accessToken = settings.getStringOrNull(PreferencesKeys.ACCESS_TOKEN)
    val refreshToken = settings.getStringOrNull(PreferencesKeys.REFRESH_TOKEN)

    val imageUrl = settings.getStringOrNull(PreferencesKeys.IMAGE_URL)

    fun getUserData(): User? {
        return try {
            Json.decodeFromString<User>(settings[PreferencesKeys.USER_DETAILS, ""])
        } catch (e: Exception) {
            Napier.e(e.message ?: "Error getting user")
            null
        }
    }

    fun startLoading() {
        toggleLoading(true)
    }

    fun stopLoading() {
        toggleLoading(false)
    }

    private fun toggleLoading(bool: Boolean) {
        settings.putBoolean(PreferencesKeys.IS_LOADING, bool)
    }

    fun getDialog(): Flow<DialogData?> {
        return settings.getStringOrNullFlow(PreferencesKeys.DIALOG_DATA)
            .map { it?.let { if (it.isNotBlank()) Json.decodeFromString<DialogData>(it) else null } }
    }

    fun showDialog(data: DialogData) {
        updateDialogData(data = data)
    }

    fun hideDialog() {
        updateDialogData(data = null)
    }

    private fun updateDialogData(data: DialogData?) {
        val string = when (data == null) {
            true -> ""
            false -> Json.encodeToString(data)
        }
        settings.putString(PreferencesKeys.DIALOG_DATA, string)
    }

    fun updateNetworkConnection(isConnected: Boolean) {
        settings.putBoolean(PreferencesKeys.HAS_NETWORK_CONNECTION, isConnected)
    }

    fun updateImageUrl(url: String) {
        settings.putString(PreferencesKeys.IMAGE_URL, url)
    }

    fun updateDarkMode(isDarkModeEnabled: Boolean) {
        settings.putBoolean(PreferencesKeys.DARK_MODE, isDarkModeEnabled)
    }

    fun userHasOnBoarded() {
        settings.putBoolean(PreferencesKeys.IS_ON_BOARDED, true)
    }

    fun signedIn(bool: Boolean) {
        settings.putBoolean(PreferencesKeys.IS_SIGNED_IN, bool)
    }

    fun userHasSetup() {
        settings.putBoolean(PreferencesKeys.IS_SETUP, true)
    }

    fun signedOut() {
        settings.putBoolean(PreferencesKeys.IS_SIGNED_IN, false)
    }

    fun updateIsUserSetup(isSetup: Boolean) {
        settings.putBoolean(PreferencesKeys.IS_SETUP, isSetup)
    }

    fun updateTokens(access: String, refresh: String) {
        settings.putString(PreferencesKeys.ACCESS_TOKEN, access)
        settings.putString(PreferencesKeys.REFRESH_TOKEN, refresh)
    }

    fun saveUserDetails(details: User) {
        val json = Json.encodeToString(details)
        settings.putString(PreferencesKeys.USER_DETAILS, json)
    }

    fun signOut() {
        settings.apply {
            putString(PreferencesKeys.IMAGE_URL, "")
            putString(PreferencesKeys.ACCESS_TOKEN, "")
            putString(PreferencesKeys.REFRESH_TOKEN, "")
            putString(PreferencesKeys.USER_DETAILS, "")
            putBoolean(PreferencesKeys.IS_SIGNED_IN, false)
            putBoolean(PreferencesKeys.IS_SETUP, false)
            putBoolean(PreferencesKeys.IS_SETUP, false)
        }
    }

    fun clear() {
        settings.clear()
    }

}