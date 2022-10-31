package com.mambo.data.dao

import com.mambo.application.utils.*
import com.mambo.data.models.ServerResponse
import com.mambo.data.requests.CommentRequest
import com.mambo.data.tables.comment.*
import com.mambo.data.tables.poem.PoemsTable
import com.mambo.data.tables.user.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.LocalDateTime
import java.util.*

class CommentsDao {

    suspend fun create(userID: UUID, poemId: UUID, request: CommentRequest): ServerResponse<out Any?> = safeTransaction(
        error = "failed creating comment"
    ) {
        if (request.content.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse("comment content cannot be blank")

        query { PoemsTable.select(PoemsTable.id eq poemId).firstOrNull() }
            ?: return@safeTransaction defaultNotFoundResponse(message = "poem not found")

        var statement: InsertStatement<Number>? = null

        query {
            statement = CommentsTable.insert {
                it[createdAt] = LocalDateTime.now()
                it[userId] = userID
                it[CommentsTable.poemId] = poemId
                it[content] = request.content
            }
        }

        val data = statement?.resultedValues?.get(0).toComment().toCommentDto()

        return@safeTransaction defaultCreatedResponse(message = "comment created", data = data)

    }

    suspend fun getComment(userId: UUID, commentId: UUID) = safeTransaction(
        error = "failed getting comment details"
    ) {
        query {

            val query = getQuery(userId = userId)
            val (liked, likes) = getLikesData(userId)

            val selectCondition = Op.build { CommentsTable.id eq commentId }

            val data = query.select(selectCondition).groupBy(CommentsTable.id, UsersTable.id, PoemsTable.id)
                .orderBy(CommentsTable.createdAt, SortOrder.DESC).map {
                    it.toCompleteCommentDto(liked = it[liked], likes = it[likes])
                }.singleOrNull()


            when (data == null) {
                true -> defaultNotFoundResponse(message = "no comment found")
                false -> defaultOkResponse(message = "comment retrieved successfully", data = data)
            }


        }
    }

    private fun getLikesData(userId: UUID): Pair<exists, Count> {
        val likes = CommentLikesTable.id.count()
        val liked =
            exists(CommentLikesTable.select { CommentLikesTable.commentId eq CommentsTable.id and (CommentLikesTable.userId eq userId) })
        return Pair(liked, likes)
    }

    private fun getQuery(userId: UUID): FieldSet {

        val like = Op.build { CommentLikesTable.commentId eq CommentsTable.id }
        val (liked, likes) = getLikesData(userId)

        val poemCondition = Op.build { CommentsTable.poemId eq PoemsTable.id }

        val columns = listOf(
            likes,
            liked,
            *CommentsTable.columns.toTypedArray(),
            *UsersTable.columns.toTypedArray(),
            *PoemsTable.columns.toTypedArray(),
        )

        return CommentsTable.innerJoin(UsersTable)
            .join(otherTable = PoemsTable, joinType = JoinType.LEFT, additionalConstraint = { poemCondition })
            .join(otherTable = CommentLikesTable, joinType = JoinType.LEFT, additionalConstraint = { like })
            .slice(columns)

    }

    suspend fun getComments(userId: UUID, poemId: UUID, page: Int) = safeTransaction(
        error = "failed getting comments"
    ) {
        query {

            val (limit, offset) = getLimitAndOffset(page)

            val query = getQuery(userId = userId)
            val (liked, likes) = getLikesData(userId)

            val selectCondition = Op.build { CommentsTable.poemId eq poemId }

            val data = query.select(selectCondition).groupBy(CommentsTable.id, UsersTable.id, PoemsTable.id)
                .orderBy(CommentsTable.createdAt, SortOrder.DESC).limit(n = limit, offset = offset).map {
                    it.toCompleteCommentDto(
                        liked = it[liked], likes = it[likes]
                    )
                }

            defaultOkResponse(message = "success", data = getPagedData(page = page, result = data))

        }
    }

    suspend fun update(userId: UUID, request: CommentRequest): ServerResponse<out Any?> = safeTransaction(
        error = "failed updating comment"
    ) {

        val commentId = request.commentId.asUUID()
            ?: return@safeTransaction defaultBadRequestResponse(message = "Invalid comment id")

        if (request.content.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse(message = "Invalid comment content")

        val result = query {

            val condition = Op.build { CommentsTable.id eq commentId and (CommentsTable.userId eq userId) }
            CommentsTable.select(condition).singleOrNull()
                ?: return@query defaultBadRequestResponse("This comment is not yours!")

            CommentsTable.update({ condition }) {
                it[updatedAt] = LocalDateTime.now()
                it[content] = request.content
            }

            null
        }

        if (result != null) return@safeTransaction result

        return@safeTransaction getComment(userId = userId, commentId = commentId)

    }

    suspend fun delete(userId: UUID, commentId: UUID) = safeTransaction(
        error = "failed deleting comment"
    ) {
        query {

            CommentsTable.select(CommentsTable.id eq commentId).firstOrNull() ?: return@query defaultNotFoundResponse(
                message = "comment not found"
            )

            val result =
                CommentsTable.deleteWhere { CommentsTable.id eq commentId and (CommentsTable.userId eq userId) }

            defaultOkResponse(message = "comment deleted", data = result != 0)

        }
    }

}