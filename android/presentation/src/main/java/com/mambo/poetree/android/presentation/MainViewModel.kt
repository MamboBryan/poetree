package com.mambo.poetree.android.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.repositories.PoetreeRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    fun getGoofyMessage()= viewModelScope.launch {
        val response = PoetreeRepository().goofy()
        Log.i("GOOFY", "RESPOSNE ->: ${response.message}")
    }

}