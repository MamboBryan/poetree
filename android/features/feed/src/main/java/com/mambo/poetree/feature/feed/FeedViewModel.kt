package com.mambo.poetree.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.domain.Poem
import com.mambo.poetree.data.repositories.PoemRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Mon 06 Mar 2023
 */
class FeedViewModel : ViewModel() {

    private val repository = PoemRepository()

    private val _poems = MutableStateFlow<List<Poem>>(listOf())
    val poems: StateFlow<List<Poem>> get() = _poems

    private var fetchJob: Job? = null

    init {
        fetch()
    }

    fun fetch() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val response = repository.getPoems(page = 1)

            if (response.isSuccessful) {
                _poems.value = response.data?.list?.map { it.toDomain() } ?: emptyList()
            }
        }
    }

}