package com.mambo.poetree.android.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.android.presentation.extensions.toString
import com.mambo.poetree.android.presentation.utils.showDialog
import com.mambo.poetree.android.presentation.utils.startLoading
import com.mambo.poetree.android.presentation.utils.stopLoading
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.remote.NetworkResult
import com.mambo.poetree.data.remote.SetupRequest
import com.mambo.poetree.data.remote.UserUpdateRequest
import com.mambo.poetree.data.repositories.UserRepository
import com.mambo.poetree.utils.DialogData
import com.mambo.poetree.utils.DialogType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class AccountViewModel : ViewModel() {

    private val repository = UserRepository()
    private val preferences = UserPreferences()

    private val _userHasSetup = preferences.isUserSetup
    val userHasSetup: Flow<Boolean> get() = _userHasSetup

    fun signOut() {
        preferences.signOut()
    }

    fun update(
        username: String,
        dateOfBirth: Date,
        gender: String,
        about: String,
        email: String? = null,
        onComplete: (success: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            when (userHasSetup.first()) {
                false -> {
                    val setupRequest = SetupRequest(
                        username = username,
                        dateOfBirth = dateOfBirth.toString("dd-MM-yyyy"),
                        gender = if (gender.equals("male", false)) 0 else 1,
                        bio = about
                    )

                    setup(request = setupRequest, onComplete = onComplete)
                }
                true -> {
                    val updateRequest = UserUpdateRequest(
                        username = username,
                        email = email,
                        dateOfBirth = dateOfBirth.toString("dd-MM-yyyy"),
                        gender = if (gender.equals("male", false)) 0 else 1,
                        bio = about
                    )

                    update(request = updateRequest, onComplete = onComplete)
                }
            }
        }
    }

    private fun setup(
        request: SetupRequest,
        onComplete: (success: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            startLoading()
            val response = repository.setup(request = request)
            stopLoading()
            handleResponse(response, onComplete)
        }
    }

    private fun update(
        request: UserUpdateRequest,
        onComplete: (success: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            startLoading()
            val response = repository.updateUser(request = request)
            stopLoading()
            handleResponse(response, onComplete)
        }
    }

    private fun handleResponse(
        response: NetworkResult<String>,
        onComplete: (success: Boolean) -> Unit
    ) {
        val (type, title, message) = when (response.isSuccessful) {
            true -> Triple(DialogType.SUCCESS, "Success", "Account updated successfully!")
            false -> Triple(DialogType.ERROR, "Error", response.message)
        }
        showDialog(
            data = DialogData(
                type = type,
                title = title,
                description = message,
                negativeAction = {
                    onComplete.invoke(response.isSuccessful)
                }
            )
        )
    }

}