package com.mambo.poetree.android.presentation.screens.home.search

import androidx.lifecycle.ViewModel
import com.mambo.poetree.data.repositories.PoemRepository
import com.mambo.poetree.data.repositories.TopicsRepository

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Mon 13 Feb 2023
 */
class SearchViewModel : ViewModel() {

    val topicsRepository = TopicsRepository()
    val poemRepository = PoemRepository()

}