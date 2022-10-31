package com.mambo.data.dao

import com.mambo.application.utils.*
import com.mambo.data.models.ServerResponse
import com.mambo.data.requests.PoemRequest
import com.mambo.data.tables.comment.CommentsTable
import com.mambo.data.tables.poem.*
import com.mambo.data.tables.topic.TopicsTable
import com.mambo.data.tables.user.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.LocalDateTime
import java.util.*

class PoemsDao {

    suspend fun create(userId: UUID, request: PoemRequest): ServerResponse<out Any?> = safeTransaction(
        error = "failed creating poem"
    ) {

        if (request.title.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse("title cannot be blank or null")
        if (request.content.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse("content cannot be blank or null")
        if (request.html.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse("html content cannot be blank or null")
        if (request.topic == null) return@safeTransaction defaultBadRequestResponse("topic id is invalid")

        val now = LocalDateTime.now()

        var statement: InsertStatement<Number>? = null

        val isInvalidTopic = query { TopicsTable.select(TopicsTable.id eq request.topic).empty() }
        if (isInvalidTopic) return@safeTransaction defaultBadRequestResponse("Invalid Topic Id")

        query {

            statement = PoemsTable.insert {
                it[createdAt] = now
                it[updatedAt] = now
                it[title] = request.title
                it[content] = request.content
                it[contentAsHtml] = request.html
                it[PoemsTable.userId] = userId
                it[topicId] = request.topic
            }

        }

        val poemId =
            statement?.resultedValues?.get(0)?.get(PoemsTable.id)?.value ?: return@safeTransaction serverErrorResponse(
                message = "unable to return poem"
            )

        getPoem(userId = userId, poemId = poemId, fromPoemCreation = true)

    }

    suspend fun update(userId: UUID, request: PoemRequest): ServerResponse<out Any?> = safeTransaction(
        error = "failed updating poems"
    ) {

        val id = request.poemId.asUUID() ?: return@safeTransaction defaultBadRequestResponse(
            message = "Invalid poem id"
        )

        if (request.title.isNullOrBlank() && request.content.isNullOrBlank() && request.html.isNullOrBlank() && request.topic == null) return@safeTransaction defaultBadRequestResponse(
            "Invalid update details, update at least one field"
        )

        val result = query {

            val condition = Op.build { PoemsTable.id eq id and (PoemsTable.userId eq userId) }

            if (request.topic != null) TopicsTable.select(TopicsTable.id eq request.topic).firstOrNull()
                ?: return@query defaultBadRequestResponse(message = "Invalid Topic Id")

            PoemsTable.select(condition).singleOrNull()
                ?: return@query defaultBadRequestResponse(message = "This poem is not yours!")

            PoemsTable.update({ condition }) {
                it[updatedAt] = LocalDateTime.now()
                it[editedAt] = LocalDateTime.now()
                if (!request.title.isNullOrBlank()) it[title] = request.title
                if (!request.content.isNullOrBlank()) it[content] = request.content
                if (!request.html.isNullOrBlank()) it[contentAsHtml] = request.html
                if (request.topic != null) it[topicId] = request.topic
            }

            null

        }

        if (result != null) return@safeTransaction result

        getPoem(userId = userId, poemId = id)

    }

    suspend fun delete(userId: UUID, poemId: UUID) = safeTransaction(
        error = "failed deleting poem"
    ) {
        query {

            val result = PoemsTable.deleteWhere { PoemsTable.id eq poemId and (PoemsTable.userId eq userId) }

            when (result != 0) {
                true -> defaultOkResponse(message = "poem deleted", data = null)
                false -> serverErrorResponse(message = "unable to delete poem")
            }

        }
    }

    suspend fun getPoem(userId: UUID, poemId: UUID, fromPoemCreation: Boolean = false) = safeTransaction(
        error = "failed getting poem details"
    ) {
        query {

            val readCondition = Op.build { ReadsTable.poemId eq PoemsTable.id }
            val reads = ReadsTable.id.count()
            val read =
                exists(ReadsTable.select { ReadsTable.poemId eq PoemsTable.id and (ReadsTable.userId eq userId) })

            val like = Op.build { PoemLikesTable.poemId eq PoemsTable.id }
            val likes = PoemLikesTable.id.count()
            val liked =
                exists(PoemLikesTable.select { PoemLikesTable.poemId eq PoemsTable.id and (PoemLikesTable.userId eq userId) })

            val bookmark = Op.build { BookmarksTable.poemId eq PoemsTable.id }
            val bookmarks = BookmarksTable.id.count()
            val bookmarked =
                exists(BookmarksTable.select { BookmarksTable.poemId eq PoemsTable.id and (BookmarksTable.userId eq userId) })

            val comment = Op.build { CommentsTable.poemId eq PoemsTable.id }
            val comments = CommentsTable.id.count()
            val commented =
                exists(CommentsTable.select { CommentsTable.poemId eq PoemsTable.id and (CommentsTable.userId eq userId) })

            val columns = listOf(
                reads,
                read,
                bookmarks,
                bookmarked,
                likes,
                liked,
                comments,
                commented,
                *UsersTable.columns.toTypedArray(),
                *TopicsTable.columns.toTypedArray(),
                *PoemsTable.columns.toTypedArray()
            )

            val poem = PoemsTable.innerJoin(UsersTable).innerJoin(TopicsTable)
                .join(otherTable = ReadsTable, joinType = JoinType.LEFT, additionalConstraint = { readCondition })
                .join(otherTable = BookmarksTable, joinType = JoinType.LEFT, additionalConstraint = { bookmark })
                .join(otherTable = PoemLikesTable, joinType = JoinType.LEFT, additionalConstraint = { like })
                .join(otherTable = CommentsTable, joinType = JoinType.LEFT, additionalConstraint = { comment })
                .slice(columns).select(PoemsTable.id eq poemId).groupBy(PoemsTable.id, UsersTable.id, TopicsTable.id)
                .map {
                    it.toCompletePoemDto(
                        read = it[read],
                        reads = it[reads],
                        bookmarked = it[bookmarked],
                        bookmarks = it[bookmarks],
                        liked = it[liked],
                        likes = it[likes],
                        commented = it[commented],
                        comments = it[comments]
                    )
                }.singleOrNull()

            when (fromPoemCreation) {
                true -> defaultCreatedResponse(message = "poem created", data = poem)
                false -> defaultOkResponse(message = "success", data = poem)
            }

        }
    }

    private fun getReadAndReads(userId: UUID): Pair<exists, Count> {
        val reads = ReadsTable.id.count()
        val read = exists(ReadsTable.select { ReadsTable.poemId eq PoemsTable.id and (ReadsTable.userId eq userId) })
        return Pair(read, reads)
    }

    private fun getBookmarkData(userId: UUID): Pair<exists, Count> {
        val bookmarks = BookmarksTable.id.count()
        val bookmarked =
            exists(BookmarksTable.select { BookmarksTable.poemId eq PoemsTable.id and (BookmarksTable.userId eq userId) })
        return Pair(bookmarked, bookmarks)
    }

    private fun getLikesData(userId: UUID): Pair<exists, Count> {
        val likes = PoemLikesTable.id.count()
        val liked =
            exists(PoemLikesTable.select { PoemLikesTable.poemId eq PoemsTable.id and (PoemLikesTable.userId eq userId) })
        return Pair(liked, likes)
    }

    private fun getCommentsData(userId: UUID): Pair<exists, Count> {
        val comments = CommentsTable.id.count()
        val commented =
            exists(CommentsTable.select { CommentsTable.poemId eq PoemsTable.id and (CommentsTable.userId eq userId) })
        return Pair(commented, comments)
    }

    private fun getQuery(userId: UUID): FieldSet {

        val readCondition = Op.build { ReadsTable.poemId eq PoemsTable.id }
        val (read, reads) = getReadAndReads(userId)

        val like = Op.build { PoemLikesTable.poemId eq PoemsTable.id }
        val (liked, likes) = getLikesData(userId)

        val bookmark = Op.build { BookmarksTable.poemId eq PoemsTable.id }
        val (bookmarked, bookmarks) = getBookmarkData(userId)

        val comment = Op.build { CommentsTable.poemId eq PoemsTable.id }
        val (commented, comments) = getCommentsData(userId)

        val columns = listOf(
            reads,
            read,
            bookmarks,
            bookmarked,
            likes,
            liked,
            comments,
            commented,
            *UsersTable.columns.toTypedArray(),
            *TopicsTable.columns.toTypedArray(),
            *PoemsTable.columns.toTypedArray()
        )

        return PoemsTable.innerJoin(UsersTable).innerJoin(TopicsTable)
            .join(otherTable = ReadsTable, joinType = JoinType.LEFT, additionalConstraint = { readCondition })
            .join(otherTable = BookmarksTable, joinType = JoinType.LEFT, additionalConstraint = { bookmark })
            .join(otherTable = PoemLikesTable, joinType = JoinType.LEFT, additionalConstraint = { like })
            .join(otherTable = CommentsTable, joinType = JoinType.LEFT, additionalConstraint = { comment })
            .slice(columns)

    }

    suspend fun getPoems(userId: UUID, topic: Int?, queryString: String?, page: Int = 1) = safeTransaction(
        error = "failed getting poems"
    ) {
        query {

            val (limit, offset) = getLimitAndOffset(page)

            val query = getQuery(userId = userId)
            val (read, reads) = getReadAndReads(userId)
            val (liked, likes) = getLikesData(userId)
            val (bookmarked, bookmarks) = getBookmarkData(userId)
            val (commented, comments) = getCommentsData(userId)

            val queryCondition =
                (PoemsTable.title.lowerCase() like "%$queryString%".lowercase()) or (PoemsTable.content.lowerCase() like "%$queryString%".lowercase())
            val topicCondition = PoemsTable.topicId eq topic

            val selectCondition = when {
                !queryString.isNullOrBlank() && topic != null -> Op.build { topicCondition and queryCondition }
                !queryString.isNullOrBlank() -> Op.build { queryCondition }
                topic != null -> Op.build { topicCondition }
                else -> null
            }

            val subQuery = when (selectCondition != null) {
                true -> query.select(selectCondition)
                false -> query.selectAll()
            }

            val data = subQuery.groupBy(PoemsTable.id, UsersTable.id, TopicsTable.id)
                .orderBy(PoemsTable.createdAt, SortOrder.DESC).limit(n = limit, offset = offset).map {
                    it.toCompletePoemDto(
                        read = it[read],
                        reads = it[reads],
                        bookmarked = it[bookmarked],
                        bookmarks = it[bookmarks],
                        liked = it[liked],
                        likes = it[likes],
                        commented = it[commented],
                        comments = it[comments]
                    )
                }

            defaultOkResponse(message = "success", data = getPagedData(page = page, result = data))

        }
    }

    suspend fun getUserPoems(currentUserId: UUID, userId: UUID, page: Int = 1) = safeTransaction(
        error = "failed fetching user poems"
    ) {
        query {

            val (limit, offset) = getLimitAndOffset(page)

            val query = getQuery(userId = currentUserId)
            val (read, reads) = getReadAndReads(currentUserId)
            val (liked, likes) = getLikesData(currentUserId)
            val (bookmarked, bookmarks) = getBookmarkData(currentUserId)
            val (commented, comments) = getCommentsData(currentUserId)

            val selectCondition = Op.build { PoemsTable.userId eq userId }

            val data = query.select(selectCondition).groupBy(PoemsTable.id, UsersTable.id, TopicsTable.id)
                .orderBy(PoemsTable.createdAt, SortOrder.DESC).limit(n = limit, offset = offset).map {
                    it.toCompletePoemDto(
                        read = it[read],
                        reads = it[reads],
                        bookmarked = it[bookmarked],
                        bookmarks = it[bookmarks],
                        liked = it[liked],
                        likes = it[likes],
                        commented = it[commented],
                        comments = it[comments]
                    )
                }

            defaultOkResponse(message = "success", data = getPagedData(page = page, result = data))


        }
    }

    suspend fun markAsRead(userId: UUID, poemId: UUID): ServerResponse<out Any?> = safeTransaction(
        error = "failed making poem as read"
    ) {

        query { PoemsTable.select(PoemsTable.id eq poemId).firstOrNull() }
            ?: return@safeTransaction defaultNotFoundResponse(message = "poem not found")

        val read = query {
            ReadsTable.select { ReadsTable.userId eq userId and (ReadsTable.poemId eq poemId) }.firstOrNull()
        }
        if (read != null) return@safeTransaction defaultOkResponse(
            message = "poem already read. \n no pun intended", data = null
        )

        query {
            ReadsTable.insert {
                it[createdAt] = LocalDateTime.now()
                it[ReadsTable.userId] = userId
                it[ReadsTable.poemId] = poemId
            }
        }

        defaultOkResponse(message = "marked as read", data = null)

    }

}