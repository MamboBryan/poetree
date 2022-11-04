package com.mambo.poetree.android.presentation.screens.account

import androidx.lifecycle.ViewModel
import com.mambo.poetree.data.local.preferences.UserPreferences

class AccountViewModel : ViewModel() {

    private val preferences = UserPreferences()

    fun signOut(){
        preferences.signOut()
    }

}