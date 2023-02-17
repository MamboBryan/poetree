package com.mambo.poetree.android.presentation.screens.home.libray

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.domain.Poem
import com.mambo.poetree.data.repositories.PoemRepository
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Wed 15 Feb 2023
 */
class LibraryViewModel : ViewModel() {

    private val repository = PoemRepository()

    var drafts by mutableStateOf(emptyList<Poem>())

    init {
        viewModelScope.launch {
            drafts = repository.getDrafts()
        }
    }

}