package com.mambo.poetree.data.domain

import com.mambo.poetree.utils.prettyCount
import com.mambo.poetree.utils.titleCase
import kotlinx.serialization.Serializable

@Serializable
data class Poem(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val type: Type,
    val html: String = content,
    val topic: Topic? = null,
    val user: User? = null,
    val reads: Long = 0,
    val read: Boolean = false,
    val bookmarks: Long = 0,
    val bookmarked: Boolean = false,
    val likes: Long = 0,
    val liked: Boolean = false,
    val comments: Long = 0,
    val commented: Boolean = false,
) {

    @Serializable
    enum class Type {
        REMOTE, BOOKMARK, DRAFT
    }

    val displayTopic: String
        get() = topic?.name.titleCase(default = "topicless")

    val displayUser : String
        get() = user?.name ?: "Me"

    val displayDate : String
        get() = "2 days ago"

    val displayReads : String = reads.prettyCount()
    val displayLikes : String = likes.prettyCount()
    val displayBookmarks : String = bookmarks.prettyCount()
    val displayComments : String = comments.prettyCount()

    fun isEditable(userId: String) = if (isDraft()) true else isMyPoem(userId = userId)

    fun isPublishable() = isDraft() and (topic != null) and (content.isNotBlank())

    fun isDraft() = type == Type.DRAFT

    fun isMyPoem(userId: String) = user?.id == userId

}



