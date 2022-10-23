package com.mambo.poetree.data.remote.dto

import com.mambo.poetree.data.domain.User
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

    val isSetup: Boolean = name != null && dateOfBirth != null

    fun toUser(): User = User(
        id,
        createdAt,
        updatedAt,
        email,
        name,
        image,
        bio,
        dateOfBirth,
        gender,
        reads,
        likes,
        bookmarks
    )
}

@Serializable
data class UserMinimalDto(
    val id: String?,
    val createdAt: String?,
    val name: String?,
    val image: String?
) {
    fun toUser(): User = User(
        id = id, createdAt = createdAt, name = name, image = image
    )
}

@Serializable
data class UserDto(
    val id: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val name: String? = null,
    val image: String? = null,
    val email: String? = null,
    val bio: String? = null,
    val dateOfBirth: String? = null,
    val gender: Int? = null
) {
    fun toUser(): User = User(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
        name = name,
        image = image,
        email = email,
        bio = bio,
        dateOfBirth = dateOfBirth,
        gender = gender
    )
}