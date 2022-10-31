package com.mambo.data.tables.comment

import com.mambo.data.tables.poem.PoemEntity
import com.mambo.data.tables.poem.PoemsTable
import com.mambo.data.tables.user.*
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

object CommentsTable : UUIDTable(name = "comments") {

    val createdAt = datetime("comment_created_at")
    val updatedAt = datetime("comment_updated_at").nullable()
    val content = text("comment_content")

    val userId = reference("comment_user_id", UsersTable, onDelete = ReferenceOption.CASCADE)
    val poemId = reference("comment_poem_id", PoemsTable, onDelete = ReferenceOption.CASCADE)

}

class CommentEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<CommentEntity>(CommentsTable)

    var createdAt by CommentsTable.createdAt
    var updatedAt by CommentsTable.updatedAt
    var content by CommentsTable.content

    var user by UserEntity referencedOn CommentsTable.userId
    var poem by PoemEntity referencedOn CommentsTable.poemId


}

/**
 * DATA CLASSES
 */

data class Comment(
    val id: UUID,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val content: String,
    val userId: UUID,
    val poemId: UUID
)

data class CommentDto(
    val id: String,
    val createdAt: String,
    val updatedAt: String?,
    val content: String,
    val userId: String,
    val poemId: String,
)

data class CommentCompleteDto(
    val id: String,
    val createdAt: String?,
    val updatedAt: String?,
    val content: String,
    val poemId: String,
    val user: UserMinimalDTO?,
    val liked: Boolean,
    val likes: Long
)

/**
 * MAPPERS/CONVERTERS
 */

internal fun ResultRow?.toComment(): Comment? {
    if (this == null) return null
    return try {
        Comment(
            id = this[CommentsTable.id].value,
            createdAt = this[CommentsTable.createdAt],
            updatedAt = this[CommentsTable.updatedAt],
            content = this[CommentsTable.content],
            userId = this[CommentsTable.userId].value,
            poemId = this[CommentsTable.poemId].value

        )
    } catch (e: Exception) {
        Napier.e("Error converting ResultRow to Comment -> ${e.localizedMessage}", e)
        null
    }

}

internal fun ResultRow?.toCompleteCommentDto(
    liked: Boolean, likes: Long
): CommentCompleteDto? {
    if (this == null) return null
    return try {

        val user = this.toUser().toMinimalUserDto()

        CommentCompleteDto(
            id = this[CommentsTable.id].value.toString(),
            createdAt = this[CommentsTable.createdAt].toDate().toDateTimeString(),
            updatedAt = this[CommentsTable.updatedAt].toDate().toDateTimeString(),
            content = this[CommentsTable.content],
            poemId = this[CommentsTable.poemId].value.toString(),
            user = user,
            liked = liked,
            likes = likes

        )
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }

}

internal fun Comment?.toCommentDto(): CommentDto? {
    if (this == null) return null
    return try {
        CommentDto(
            id = this.id.toString(),
            createdAt = this.createdAt.toDate().toDateTimeString()!!,
            updatedAt = this.updatedAt.toDate().toDateTimeString(),
            userId = this.userId.toString(),
            poemId = this.poemId.toString(),
            content = this.content
        )
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        null
    }
}