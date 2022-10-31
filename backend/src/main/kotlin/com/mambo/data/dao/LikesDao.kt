package com.mambo.data.dao

import com.mambo.application.utils.*
import com.mambo.data.models.ServerResponse
import com.mambo.data.tables.comment.CommentLikesTable
import com.mambo.data.tables.comment.CommentsTable
import com.mambo.data.tables.poem.PoemLikesTable
import com.mambo.data.tables.poem.PoemsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime
import java.util.*

class LikesDao {

    suspend fun likePoem(userId: UUID, poemId: UUID): ServerResponse<out Any?> = safeTransaction(
        error = "failed liking poem"
    ) {

        query { PoemsTable.select(PoemsTable.id eq poemId).firstOrNull() }
            ?: return@safeTransaction defaultNotFoundResponse(message = "poem not found")

        val liked = query {
            PoemLikesTable.select { PoemLikesTable.userId eq userId and (PoemLikesTable.poemId eq poemId) }
                .firstOrNull()
        }
        if (liked != null) return@safeTransaction defaultOkResponse(message = "poem already liked", data = null)

        query {
            PoemLikesTable.insert {
                it[createdAt] = LocalDateTime.now()
                it[PoemLikesTable.userId] = userId
                it[PoemLikesTable.poemId] = poemId
            }
        }

        defaultCreatedResponse(message = "poem liked", data = null)

    }

    suspend fun unlikePoem(userId: UUID, poemId: UUID) = safeTransaction(
        error = "failed unliking poem"
    ) {
        query {

            PoemsTable.select(PoemsTable.id eq poemId).firstOrNull()
                ?: return@query defaultNotFoundResponse(message = "poem not found")

            val result =
                PoemLikesTable.deleteWhere { PoemLikesTable.userId eq userId and (PoemLikesTable.poemId eq poemId) }

            defaultOkResponse(message = "poem unliked", data = result != 0)

        }
    }

    suspend fun likeComment(userId: UUID, commentId: UUID): ServerResponse<out Any?> = safeTransaction(
        error = "failed liking comment"
    ) {

        query { CommentsTable.select(CommentsTable.id eq commentId).firstOrNull() }
            ?: return@safeTransaction defaultNotFoundResponse(message = "comment not found")

        val liked = query {
            CommentLikesTable.select { CommentLikesTable.userId eq userId and (CommentLikesTable.commentId eq commentId) }
                .firstOrNull()
        }
        if (liked != null) return@safeTransaction defaultOkResponse(message = "comment already liked", data = null)

        query {
            CommentLikesTable.insert {
                it[createdAt] = LocalDateTime.now()
                it[CommentLikesTable.userId] = userId
                it[CommentLikesTable.commentId] = commentId
            }
        }

        defaultCreatedResponse(message = "comment liked", data = null)

    }

    suspend fun unlikeComment(userId: UUID, commentId: UUID) = safeTransaction(
        error = "failed unliking comment"
    ) {
        query {

            CommentsTable.select(CommentsTable.id eq commentId).firstOrNull()
                ?: return@query defaultNotFoundResponse(message = "comment not found")

            val result = CommentLikesTable.deleteWhere {
                CommentLikesTable.userId eq userId and (CommentLikesTable.commentId eq commentId)
            }

            defaultOkResponse(message = "comment unliked", data = result != 0)

        }
    }

}