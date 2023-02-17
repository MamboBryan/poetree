package com.mambo.poetree.data.domain

import com.mambo.poetree.data.local.entity.DraftRealm
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

    companion object {
        fun String.asPoem() = Json.decodeFromString<Poem?>(this)
    }

    @Serializable
    enum class Type {
        REMOTE, BOOKMARK, DRAFT
    }

    fun toDraft() =
        DraftRealm(id = id, title = title, content = content, topic = Json.encodeToString(topic))

    fun asJson() = Json.encodeToString(this)

}



