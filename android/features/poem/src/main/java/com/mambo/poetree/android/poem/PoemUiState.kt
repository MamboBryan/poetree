package com.mambo.poetree.android.poem

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Thu 02 Mar 2023
 */
internal sealed class PoemUiState {
    object Loading : PoemUiState()
    object Error : PoemUiState()
    object Success : PoemUiState()
}