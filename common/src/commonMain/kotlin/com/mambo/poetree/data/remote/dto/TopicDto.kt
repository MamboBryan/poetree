package com.mambo.poetree.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    val id: Int,
    val name: String,
    val color: String,
    val createdAt: String?,
    val updatedAt: String?,
)
