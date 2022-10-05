package com.mambo.poetree.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: String,
    val createdAt: String,
    val updatedAt: String?,
    val content: String,
    val userId: String,
    val poemId: String
)

@Serializable
data class CommentCompleteDto(
    val id: String,
    val createdAt: String,
    val updatedAt: String?,
    val content: String,
    val poemId: String,
    val user: UserMinimalDto,
    val liked: Boolean,
    val likes: Long
)