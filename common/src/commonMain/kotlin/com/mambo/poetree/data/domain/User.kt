package com.mambo.poetree.data.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val email: String? = null,
    val name: String? = null,
    val image: String? = null,
    val bio: String? = null,
    val dateOfBirth: String? = null,
    val gender: Int? = null,
    val reads: Long? = null,
    val likes: Long? = null,
    val bookmarks: Long? = null
){
    val isSetup = name != null && dateOfBirth != null
}