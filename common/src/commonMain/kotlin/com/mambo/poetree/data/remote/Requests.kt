package com.mambo.poetree.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String,
    val password: String
)

@Serializable
data class SetupRequest(
    val username: String,
    val dateOfBirth: String,
    val gender: Int,
    val bio: String
)

@Serializable
data class UserUpdateRequest(
    val username: String?,
    val email: String?,
    val dateOfBirth: String?,
    val gender: Int?,
    val bio: String?
)


@Serializable
data class UpdatePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)

@Serializable
data class TopicRequest(
    val name: String?,
    val color: String?
)

@Serializable
data class EditPoemRequest(
    val poemId: String,
    val title: String?,
    val content: String?,
    val html: String?,
    val topic: Int?,
)

@Serializable
data class CreatePoemRequest(
    val title: String,
    val content: String,
    val html: String,
    val topic: Int,
)

@Serializable
data class PoemRequest(
    val poemId: String,
)


@Serializable
data class CreateCommentRequest(
    val poemId: String,
    val content: String
)

@Serializable
data class UpdateCommentRequest(
    val commentId: String,
    val content: String
)