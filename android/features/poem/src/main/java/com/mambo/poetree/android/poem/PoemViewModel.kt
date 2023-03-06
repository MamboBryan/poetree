package com.mambo.poetree.android.poem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.domain.Poem
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.repositories.PoemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun 19 Feb 2023
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PoemViewModel(
    val app: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(app) {

    private val repository = PoemRepository()
    private val preferences = UserPreferences()

    val id = savedStateHandle.get<String>("id")
    val type = savedStateHandle.get<String>("type")

    private val userId = preferences.getUserData()?.id ?: ""
    private var fetchJob: Job? = null

    private val _poem = MutableStateFlow<Poem?>(null)
    val poem: StateFlow<Poem?> get() = _poem

    private val _comment = MutableStateFlow<String>("")
    val comment: StateFlow<String> get() = _comment

    val isEditable = _poem.mapLatest { it?.isEditable(userId = userId) ?: false }
    val isPublishable = _poem.mapLatest { it?.isPublishable() ?: false }

    init {
        fetch()
    }

    fun fetch() {
        if (type != null) {
            val poemType = Poem.Type.valueOf(type)
            when (poemType) {
                Poem.Type.REMOTE -> fetchRemote()
                Poem.Type.BOOKMARK -> fetchBookmark()
                Poem.Type.DRAFT -> fetchDraft()
            }
        }
    }

    fun updateComment(s: String) {
        _comment.value = s
    }

    private fun fetchRemote() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val response = repository.getPublished(poemId = id ?: "")
            if (response.isSuccessful.not())
                return@launch

            updatePoem(response.data)

        }
    }

    private fun fetchBookmark() {
//        fetchJob?.cancel()
//        fetchJob = viewModelScope.launch {
//            val response = repository.getDraft(poemId = id ?: "")
//            if(response.isSuccessful.not())
//                return@launch
//
//            _poem.value = response.data
//        }
    }

    private fun fetchDraft() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val poem = repository.getDraft(id = id ?: "")
            updatePoem(poem)
        }
    }

    private fun updatePoem(update: Poem?) {
        _poem.value = update
    }

}