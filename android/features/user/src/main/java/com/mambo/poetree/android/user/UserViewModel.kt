package com.mambo.poetree.android.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.data.domain.Poem
import com.mambo.poetree.data.domain.User
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.repositories.PoemRepository
import com.mambo.poetree.data.repositories.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 11 Mar 2023
 */
class UserViewModel(val app: Application, val saved: SavedStateHandle) : AndroidViewModel(app) {

    private val id = saved.get<String>("id")

    private val userRepository = UserRepository()
    private val poemRepository = PoemRepository()

    private val _poems = MutableStateFlow(listOf<Poem>())
    val poems get() = _poems.asStateFlow()

    private val _details = MutableStateFlow<User?>(null)
    val details get() = _details.asStateFlow()

    private var _fetchPoemsJob: Job? = null
    private var _fetchDetailsJob: Job? = null

    private val myDetails = UserPreferences().getUserData()

    init {
        getDetails()
        getPoems()
    }

    fun getDetails(){
        _fetchDetailsJob?.cancel()
        if (id == null) {
            _details.value = myDetails
            getMyDetails()
        }
        else
            getUserDetails()
    }

    private fun getMyDetails(){
        _fetchDetailsJob = viewModelScope.launch {
            val response = userRepository.getMyDetails()
            if (response.isSuccessful.not())
                return@launch

            _details.value = response.data?.toUser()
        }
    }

    private fun getUserDetails(){
        if (id != null)
            _fetchDetailsJob = viewModelScope.launch {
                val response = userRepository.getUserDetails(userId = id)
                if (response.isSuccessful.not())
                    return@launch
                _details.value = response.data?.toUser()
            }
    }

    fun getPoems() {
        _fetchPoemsJob?.cancel()
        if (id == null)
            getMyPoems()
        else
            getUserPoems()
    }

    private fun getMyPoems() {
        _fetchPoemsJob = viewModelScope.launch {
            val response = poemRepository.getMyPoems()
            if (response.isSuccessful.not())
                return@launch
            _poems.value = response.data?.list?.map { it.toDomain() } ?: listOf()
        }
    }

    private fun getUserPoems() {
        if (id != null)
            _fetchPoemsJob = viewModelScope.launch {
                val response = poemRepository.getUserPoems(userId = id)
                if (response.isSuccessful.not())
                    return@launch
                _poems.value = response.data?.list?.map { it.toDomain() } ?: listOf()
            }
    }

}