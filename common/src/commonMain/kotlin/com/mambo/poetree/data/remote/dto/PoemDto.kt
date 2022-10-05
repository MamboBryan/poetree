package com.mambo.poetree.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PoemDto(
    var id: String,
    var createdAt: String,
    var updatedAt: String,
    var editedAt: String?,
    var title: String,
    var content: String,
    var html: String,
    var user: UserDto,
    var topic: TopicDto,
    var reads: Long,
    var read: Boolean,
    var bookmarks: Long,
    var bookmarked: Boolean,
    var likes: Long,
    var liked: Boolean,
    var comments: Long,
    var commented: Boolean
)