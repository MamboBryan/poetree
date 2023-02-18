package com.mambo.poetree.android.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.AppMonitor.hideLoading
import com.mambo.poetree.AppMonitor.showLoading
import com.mambo.poetree.data.repositories.AuthRepository
import com.mambo.poetree.showDialog
import com.mambo.poetree.utils.DialogData
import com.mambo.poetree.utils.DialogType
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    fun signIn(email: String, password: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            showLoading()
            val response = repository.signIn(email = email, password = password)
            hideLoading()
            when (response.isSuccessful) {
                false -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.ERROR,
                            title = "Authentication Error",
                            description = response.message,
                            positiveText = "dismiss"
                        )
                    )
                }
                true -> {
                    showDialog(data = DialogData(
                        type = DialogType.SUCCESS,
                        title = "Authenticated Successfully",
                        description = response.message,
                        positiveText = "continue",
                        positiveAction = {
                            onSuccess.invoke(response.data == true)
                        }
                    ))
                }
            }
        }
    }

    fun signUp(email: String, password: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            showLoading()
            val response = repository.signUp(email = email, password = password)
            hideLoading()
            when (response.isSuccessful) {
                false -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.ERROR,
                            title = "Failed Signing Up",
                            description = response.message,
                            positiveText = "dismiss"
                        )
                    )
                }
                true -> {
                    showDialog(data = DialogData(
                        type = DialogType.SUCCESS,
                        title = "Account Created Successfully",
                        description = response.message,
                        positiveText = "continue",
                        positiveAction = {
                            onSuccess.invoke(response.data == true)
                        }
                    ))
                }
            }
        }
    }

}