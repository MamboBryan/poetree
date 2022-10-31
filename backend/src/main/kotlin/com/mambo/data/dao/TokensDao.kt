package com.mambo.data.dao

import com.mambo.application.utils.*
import com.mambo.data.models.ServerResponse
import com.mambo.data.tables.tokens.TokensTable
import com.mambo.data.tables.topic.TopicsTable
import com.mambo.data.tables.topic.toTopic
import com.mambo.data.tables.topic.toTopicDto
import io.github.aakira.napier.Napier
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.util.*

class TokensDao {

    suspend fun save(token: String, userId: UUID, expiry: String): ServerResponse<out Any?> = safeTransaction(
        error = "failed while saving token"
    ) {

        var statement: InsertStatement<Number>? = null

        query {
            statement = TokensTable.insert {
                it[TokensTable.user] = userId
                it[TokensTable.token] = token
                it[TokensTable.expiration] = expiry
            }
        }

        val data = statement?.resultedValues?.get(0)

        defaultCreatedResponse(message = "token saved successfully", data = data != null)

    }

    suspend fun delete(tokenId: UUID) = safeTransaction {
        query {

            val tokenExists = TokensTable.select { TokensTable.id eq tokenId }.empty().not()
            if (!tokenExists) return@query defaultNotFoundResponse(message = "token not found")
            val result = TokensTable.deleteWhere { TokensTable.id eq tokenId }
            defaultOkResponse(message = "token deleted successfully", data = result != 0)

        }
    }

    suspend fun delete(tokenId: String) = safeTransaction {
        query {

            val tokenExists = TokensTable.select { TokensTable.token eq tokenId }.empty().not()
            if (!tokenExists) return@query defaultNotFoundResponse(message = "token not found")
            val result = TokensTable.deleteWhere { TokensTable.token eq tokenId }
            defaultOkResponse(message = "token deleted successfully", data = result != 0)

        }
    }

    suspend fun deleteAllUserTokens(userId: UUID) {
        try {
            query {
                TokensTable.deleteWhere { TokensTable.user eq userId }
            }
        } catch (e: Exception) {
            Napier.e("error while deleting tokens", e)
        }
    }

    suspend fun deleteExpiredTokens(userId: UUID) {
        try {
            val currentTime = Date(System.currentTimeMillis()).toDateTimeString()!!
            query {
                TokensTable.select { TokensTable.user eq userId }.forEach {
                    val time = it[TokensTable.expiration]
                    if (time < currentTime) TokensTable.deleteWhere { TokensTable.id eq it[TokensTable.id] }
                }
            }
        } catch (e: Exception) {
            Napier.e("error while deleting tokens", e)
        }
    }

    suspend fun exists(userId: UUID, token: String): Boolean {
        return try {
            query { TokensTable.select { TokensTable.user eq userId and (TokensTable.token eq token) }.empty().not() }
        } catch (e: Exception) {
            Napier.e("error while checking if token exists", e)
            false
        }
    }

}