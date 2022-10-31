package com.mambo.data.dao

import com.mambo.application.utils.*
import com.mambo.data.models.ServerResponse
import com.mambo.data.tables.poem.BookmarksTable
import com.mambo.data.tables.poem.PoemLikesTable
import com.mambo.data.tables.poem.ReadsTable
import com.mambo.data.tables.user.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.LocalDateTime
import java.util.*

class UsersDao {

    suspend fun create(email: String, hash: String): ServerResponse<out Any?> = safeTransaction(
        error = "invalid user email or password"
    ) {

        var statement: InsertStatement<Number>? = null

        val exists = query { UsersTable.select { UsersTable.userEmail eq email }.firstOrNull() } != null

        if (exists) return@safeTransaction defaultBadRequestResponse(message = "User with email already exists")

        query {
            statement = UsersTable.insert {
                it[userCreatedAt] = LocalDateTime.now()
                it[userUpdatedAt] = LocalDateTime.now()
                it[userEmail] = email
                it[userHash] = hash
            }
        }

        val userId =
            statement?.resultedValues?.get(0)?.get(UsersTable.id)?.value ?: return@safeTransaction serverErrorResponse(
                message = "unable to return user details"
            )

        getUser(userId = userId, formatToDto = false)

    }

    suspend fun updatePassword(id: UUID, hash: String): ServerResponse<out Any?> = safeTransaction(
        error = "unable to update password"
    ) {
        query {
            UsersTable.update({ UsersTable.id eq id }) {
                it[userUpdatedAt] = LocalDateTime.now()
                it[userHash] = hash
            }

            defaultCreatedResponse(message = "password updated", data = null)
        }
    }

    suspend fun update(
        id: UUID,
        email: String?,
        username: String?,
        bio: String?,
        dateOfBirth: String?,
        gender: Int?,
        imageUrl: String? = null,
        token: String? = null
    ): ServerResponse<out Any?> = safeTransaction(
        error = "unable to update user details"
    ) {
        query {

            val now = LocalDateTime.now()
            val dobAsDate = dateOfBirth.toDate()

            if (email.isNullOrBlank().not()) {
                val exists =
                    UsersTable.select { UsersTable.userEmail eq email!! and (UsersTable.id neq id) }.empty().not()

                if (exists) return@query defaultBadRequestResponse(message = "Email already registered to another account.")
            }

            UsersTable.update({ UsersTable.id eq id }) {
                it[userUpdatedAt] = now
                it[userSetupAt] = now
                if (!email.isNullOrBlank()) it[userEmail] = email
                if (!username.isNullOrBlank()) it[userName] = username
                if (!bio.isNullOrBlank()) it[userBio] = bio
                if (dobAsDate != null) it[userDateOfBirth] = dobAsDate.asLocalDate()
                if (gender != null) it[userGender] = gender
                if (!imageUrl.isNullOrBlank()) it[userImage] = imageUrl
                if (!token.isNullOrBlank()) it[userToken] = token
            }

        }

        getUserDetails(userId = id)

    }

    suspend fun getUser(userId: UUID, formatToDto: Boolean) = safeTransaction(
        error = "unable to get user details"
    ) {
        query {
            val user = UsersTable.select(UsersTable.id eq userId).map {
                if (formatToDto) it.toUser().toUserDto()
                else it.toUser()
            }.singleOrNull()

            defaultOkResponse(message = "success", data = user)

        }
    }

    suspend fun getUserDetails(userId: UUID) = safeTransaction(
        error = "unable to get user details"
    ) {
        query {

            val read = Op.build { ReadsTable.userId eq UsersTable.id }
            val reads = ReadsTable.id.count()

            val like = Op.build { PoemLikesTable.userId eq UsersTable.id }
            val likes = PoemLikesTable.id.count()

            val bookmark = Op.build { BookmarksTable.userId eq UsersTable.id }
            val bookmarks = BookmarksTable.id.count()

            val columns = listOf(reads, bookmarks, likes, *UsersTable.columns.toTypedArray())

            val data =
                UsersTable.join(otherTable = ReadsTable, joinType = JoinType.LEFT, additionalConstraint = { read })
                    .join(otherTable = BookmarksTable, joinType = JoinType.LEFT, additionalConstraint = { bookmark })
                    .join(otherTable = PoemLikesTable, joinType = JoinType.LEFT, additionalConstraint = { like })
                    .slice(columns).select(UsersTable.id eq userId).groupBy(UsersTable.id).map {
                        it.toUser().toCompleteUserDto(reads = it[reads], bookmarks = it[bookmarks], likes = it[likes])
                    }.singleOrNull()

            defaultOkResponse(message = "success", data = data)

        }
    }

    suspend fun getUsers(userId: UUID, query: String, page: Int = 1) = safeTransaction(
        error = "failed getting users list"
    ) {
        query {

            val (limit, offset) = getLimitAndOffset(page)

            val condition = Op.build {
                when (query.isBlank()) {
                    true -> {
                        UsersTable.id neq userId
                    }

                    false -> {
                        UsersTable.id neq userId and (UsersTable.userName.lowerCase() like "%$query%".lowercase() or (UsersTable.userEmail.lowerCase() like "%$query%".lowercase()))
                    }
                }
            }

            val users = UsersTable.select { condition }.limit(n = limit, offset = offset).toUserList()
                .map { it.toMinimalUserDto() }

            val data = getPagedData(page = page, result = users)

            defaultOkResponse(message = "users got successfully", data = data)

        }
    }

    suspend fun getUserByEmail(email: String) = safeTransaction(
        error = "failed getting user"
    ) {
        query {

            val result = UsersTable.select { UsersTable.userEmail eq email }
                .map { it.toUser() }
                .singleOrNull()

            when (result != null) {
                false -> defaultNotFoundResponse(message = "user with email doesn't exist")
                true -> defaultOkResponse(message = "user got successfully", data = result)
            }

        }
    }

    suspend fun checkValidCredentials(email: String, hashPassword: String): ServerResponse<Boolean?> = safeTransaction(
        error = "invalid credential"
    ) {
        query {

            val user = UsersTable.select { UsersTable.userEmail eq email }.map { it.toUser() }.singleOrNull()

            return@query when (user == null) {
                true -> ServerResponse(status = HttpStatusCode.Unauthorized, message = "", data = false)
                false -> ServerResponse(
                    status = HttpStatusCode.Unauthorized,
                    message = "",
                    data = user.hash == hashPassword
                )
            }

        }
    }

    suspend fun delete(userId: UUID) = safeTransaction(
        error = "failed deleting user details"
    ) {
        query {
            val result = UsersTable.deleteWhere { UsersTable.id eq userId }
            defaultOkResponse(message = "user deleted", data = result != 0)
        }
    }

}