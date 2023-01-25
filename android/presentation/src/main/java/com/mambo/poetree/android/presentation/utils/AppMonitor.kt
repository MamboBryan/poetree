package com.mambo.poetree.android.presentation.utils

import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Fri 06 Jan 2023
 */

fun showDialog(data: DialogData) {
    AppMonitor.showDialog(data = data)
}

fun dismissDialog() {
    AppMonitor.hideDialog()
}

fun startLoading() {
    AppMonitor.showLoading()
}

fun stopLoading() {
    AppMonitor.hideLoading()
}

object AppMonitor {

    private val preferences = UserPreferences()
    private val DIALOG = MutableStateFlow<DialogData?>(null)
    val dialog get() : StateFlow<DialogData?> = DIALOG

    internal fun showLoading(){
        preferences.startLoading()
    }

    internal fun hideLoading(){
        preferences.stopLoading()
    }

    internal fun showDialog(data: DialogData){
        DIALOG.value = data
    }

    internal fun hideDialog(){
        DIALOG.value = null
    }

}