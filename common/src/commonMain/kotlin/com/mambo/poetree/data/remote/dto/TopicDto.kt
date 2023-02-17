package com.mambo.poetree.data.remote.dto

import com.mambo.poetree.data.domain.Topic
import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(
    val id: Int,
    val name: String,
    val color: String,
    val createdAt: String?,
    val updatedAt: String?,
) {

    fun toDomain() = Topic(
        id = id,
        name = name,
        color = color,
        createdAt = createdAt ?: "",
        updatedAt = updatedAt
    )

}
