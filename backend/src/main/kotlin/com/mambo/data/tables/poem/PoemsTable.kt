package com.mambo.data.tables.poem

import com.mambo.data.tables.topic.*
import com.mambo.data.tables.topic.toTopic
import com.mambo.data.tables.user.*
import com.mambo.data.tables.user.toUser
import com.mambo.application.utils.toDate
import com.mambo.application.utils.toDateTimeString
import io.github.aakira.napier.Napier
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object PoemsTable : UUIDTable(name = "poems") {

    val createdAt = datetime("poem_created_at")
    val updatedAt = datetime("poem_updated_at")
    val editedAt = datetime("poem_edited_at").nullable()

    val title = text("poem_title")
    val content = text("poem_content")
    val contentAsHtml = text("poem_content_html")

    val userId = reference("poem_user_id", UsersTable, onDelete = ReferenceOption.CASCADE)
    val topicId = reference("poem_topic_id", TopicsTable, onDelete = ReferenceOption.CASCADE)

}

class PoemEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<PoemEntity>(PoemsTable)

    var createdAt by PoemsTable.createdAt
    var updatedAt by PoemsTable.updatedAt
    var editedAt by PoemsTable.editedAt

    var title by PoemsTable.title
    var content by PoemsTable.content
    var contentAsHtml by PoemsTable.contentAsHtml

}

data class Poem(
    val id: UUID,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val editedAt: LocalDateTime?,
    val title: String,
    val content: String,
    val contentAsHtml: String,
    val userId: UUID,
    val topicId: Int
)

data class CompletePoemDto(
    val id: String,
    val createdAt: String?,
    val updatedAt: String?,
    val editedAt: String?,
    val title: String?,
    val content: String?,
    val html: String?,
    val user: UserDto?,
    val topic: TopicDto?,
    val reads: Long,
    val read: Boolean = false,
    val bookmarks: Long,
    val bookmarked: Boolean = false,
    val likes: Long,
    val liked: Boolean = false,
    val comments: Long,
    val commented: Boolean = false
)

internal fun ResultRow?.toPoem(): Poem? {
    if (this == null) return null
    return Poem(
        id = this[PoemsTable.id].value,
        createdAt = this[PoemsTable.createdAt],
        updatedAt = this[PoemsTable.updatedAt],
        editedAt = this[PoemsTable.editedAt],
        title = this[PoemsTable.title],
        content = this[PoemsTable.content],
        contentAsHtml = this[PoemsTable.contentAsHtml],
        userId = this[PoemsTable.userId].value,
        topicId = this[PoemsTable.topicId].value,
    )
}

internal fun ResultRow?.toCompletePoemDto(
    reads: Long,
    read: Boolean = false,
    bookmarks: Long,
    bookmarked: Boolean = false,
    likes: Long,
    liked: Boolean = false,
    comments: Long,
    commented: Boolean = false
): CompletePoemDto? {
    if (this == null) return null

    return try {

        val user = this.toUser().toUserDto()
        val topic = this.toTopic().toTopicDto()

        CompletePoemDto(
            id = this[PoemsTable.id].value.toString(),
            createdAt = this[PoemsTable.createdAt].toDate().toDateTimeString(),
            updatedAt = this[PoemsTable.updatedAt].toDate().toDateTimeString(),
            editedAt = this[PoemsTable.editedAt].toDate().toDateTimeString(),
            title = this[PoemsTable.title],
            content = this[PoemsTable.content],
            html = this[PoemsTable.contentAsHtml],
            user = user,
            topic = topic,
            reads = reads,
            read = read,
            bookmarks = bookmarks,
            bookmarked = bookmarked,
            likes = likes,
            liked = liked,
            comments = comments,
            commented = commented
        )

    } catch (e: Exception) {
        Napier.e("Error changing from result row to Complete Poem -> ${e.localizedMessage}", e)
        null
    }

}