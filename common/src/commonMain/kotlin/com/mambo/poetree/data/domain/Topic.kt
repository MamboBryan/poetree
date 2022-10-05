package com.mambo.poetree.data.domain

import com.mambo.poetree.data.local.entity.TopicEntity
import com.mambo.poetree.data.remote.dto.TopicDto

data class Topic(
    val id: Int,
    val name: String,
    val color: String,
    val createdAt: String,
    val updatedAt: String?,
) {

    fun toEntity() = TopicEntity().apply {
        id = this@Topic.id
        name = this@Topic.name
        color = this@Topic.color
        createdAt = this@Topic.createdAt
        updatedAt = this@Topic.updatedAt
    }

    fun toDto() = TopicDto(
        id = id, name = name, color = color, createdAt = createdAt, updatedAt = updatedAt
    )

}