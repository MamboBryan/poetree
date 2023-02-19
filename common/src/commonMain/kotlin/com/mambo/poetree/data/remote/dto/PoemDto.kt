package com.mambo.poetree.data.remote.dto

import com.mambo.poetree.data.domain.Poem
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
) {

    fun toDomain() = Poem(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
        title = title,
        content = content,
        html = html,
        user = user.toUser(),
        topic = topic.toDomain(),
        reads = reads,
        read = read,
        bookmarks = bookmarks,
        bookmarked = bookmarked,
        likes = likes,
        liked = liked,
        comments = comments,
        commented = commented,
        type = Poem.Type.REMOTE
    )

}