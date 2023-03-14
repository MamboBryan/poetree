package com.mambo.poetree.android.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 11 Mar 2023
 */
class UserViewModel(val app: Application, val saved: SavedStateHandle) : AndroidViewModel(app) {
}