package com.mambo.poetree.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserCompleteDto(
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
) {
    val isSetup = name != null && dateOfBirth != null
}

@Serializable
data class UserMinimalDto(
    val id: String?,
    val createdAt: String?,
    val name: String?,
    val image: String?
)

@Serializable
data class UserDto(
    var id: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null,
    var name: String? = null,
    var image: String? = null,
    var email: String? = null,
    var bio: String? = null,
    var dateOfBirth: String? = null,
    var gender: Int? = null
)