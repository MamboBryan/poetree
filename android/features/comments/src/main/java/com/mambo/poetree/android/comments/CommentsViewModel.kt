package com.mambo.poetree.android.comments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.remote.dto.CommentCompleteDto
import com.mambo.poetree.data.repositories.CommentRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 11 Mar 2023
 */
class CommentsViewModel(
    val app: Application,
    val saved: SavedStateHandle
) : AndroidViewModel(app) {

    private val id = saved.get<String>("id")
    private val mTitle = saved.get<String>("title")
    private val mCount = saved.get<Long>("count")

    private val repository = CommentRepository()

    private val _title = MutableStateFlow(mTitle)
    val title get() = _title.asStateFlow()

    private val _count = MutableStateFlow(mCount)
    val count get() = _count.asStateFlow()

    private val _comments = MutableStateFlow<List<CommentCompleteDto>>(listOf())
    val comments get() = _comments.asStateFlow()

    private val _comment = MutableStateFlow<String>("")
    val comment: StateFlow<String> get() = _comment

    private var _fetchJob: Job? = null

    init {
        getComments()
    }

    fun getComments(){
        if(id != null) {
            _fetchJob?.cancel()
            _fetchJob = viewModelScope.launch {
                val response = repository.getComment(poemId = id, page = 1)
                if (response.isSuccessful.not())
                    return@launch
                val data = response.data
                _comments.value = data?.list ?: listOf()
            }
        }
    }

    fun updateComment(s: String) {
        _comment.value = s
    }

}