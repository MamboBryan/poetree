package com.mambo.data.dao

import com.mambo.application.utils.*
import com.mambo.data.models.ServerResponse
import com.mambo.data.tables.poem.BookmarksTable
import com.mambo.data.tables.poem.PoemsTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime
import java.util.*

class BookmarkDao {

    suspend fun create(userId: UUID, poemId: UUID): ServerResponse<out Any?> = safeTransaction(
        error = "failed creating bookmark"
    ) {

        query { PoemsTable.select(PoemsTable.id eq poemId).firstOrNull() }
            ?: return@safeTransaction defaultNotFoundResponse(message = "poem not found")

        val bookmarked = query {
            BookmarksTable.select { BookmarksTable.userId eq userId and (BookmarksTable.poemId eq poemId) }
                .firstOrNull()
        }
        if (bookmarked != null) return@safeTransaction defaultOkResponse(
            message = "poem already bookmarked", data = null
        )

        query {
            BookmarksTable.insert {
                it[createdAt] = LocalDateTime.now()
                it[BookmarksTable.userId] = userId
                it[BookmarksTable.poemId] = poemId
            }
        }

        defaultCreatedResponse(message = "poem bookmarked", data = null)

    }

    suspend fun delete(userId: UUID, poemId: UUID) = safeTransaction(
        error = "failed deleting bookmark"
    ) {
        query {

            PoemsTable.select(PoemsTable.id eq poemId).firstOrNull()
                ?: return@query defaultNotFoundResponse(message = "poem not found")

            val result =
                BookmarksTable.deleteWhere { BookmarksTable.userId eq userId and (BookmarksTable.poemId eq poemId) }

            defaultOkResponse(message = "poem bookmark deleted", data = result != 0)

        }
    }

}