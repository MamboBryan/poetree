package com.mambo.poetree.android.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var success by mutableStateOf<String?>(null)

    var hasSetup by mutableStateOf<Boolean?>(null)

    fun signIn(email: String, password: String) = viewModelScope.launch {
        isLoading = true
        val response = repository.signIn(email = email, password = password)
        isLoading = false
        when (response.isSuccessful) {
            false -> error = response.message
            true -> {
                success = response.message
                hasSetup = response.data
            }
        }
    }

    fun signUp(email: String, password: String) = viewModelScope.launch {
        isLoading = true
        val response = repository.signUp(email = email, password = password)
        isLoading = false
        when (response.isSuccessful) {
            false -> {
                error = response.message
            }
            true -> {
                success = response.message
                hasSetup = response.data
            }
        }


    }

}