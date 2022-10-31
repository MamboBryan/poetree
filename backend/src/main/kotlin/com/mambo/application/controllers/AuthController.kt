package com.mambo.application.controllers

import com.mambo.application.utils.*
import com.mambo.data.dao.TokensDao
import com.mambo.data.dao.UsersDao
import com.mambo.data.helpers.PasswordProvider
import com.mambo.data.helpers.TokenProvider
import com.mambo.data.models.ServerResponse
import com.mambo.data.requests.AuthRequest
import com.mambo.data.requests.RefreshTokenRequest
import com.mambo.data.requests.ResetRequest
import com.mambo.data.tables.user.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*

class AuthController {

    private val tokensDao = TokensDao()
    private val usersDao = UsersDao()

    suspend fun signIn(
        call: ApplicationCall, issuer: String, audience: String
    ): ServerResponse<Any?> = safeTransaction(error = "failed signing in") {

        val request = call.receive<AuthRequest>()

        if (request.email.isNullOrBlank() or request.password.isNullOrBlank())
            return@safeTransaction defaultBadRequestResponse(message = "Email or Password cannot be blank")

        val response = usersDao.getUserByEmail(request.email!!)

        if (response.status.isSuccess().not()) return@safeTransaction response

        val user = response.data as User

        if (PasswordProvider.isValidHashPassword(password = request.password!!, hashPassword = user.hash).not())
            return@safeTransaction defaultBadRequestResponse(message = "Invalid Credentials")

        val tokensData = TokenProvider.generateTokens(
            issuer = issuer, audience = audience, userId = user.id!!.toString()
        )

        val refreshTokenExpiry =
            TokenProvider.getTokenExpiryDate(issuer = issuer, audience = audience, token = tokensData.refreshToken)

        tokensDao.save(
            token = tokensData.refreshToken,
            userId = user.id,
            expiry = refreshTokenExpiry.toDateTimeString()!!
        )

        val userData = usersDao.getUserDetails(userId = user.id)

        if (userData.status.isSuccess().not())
            return@safeTransaction ServerResponse(
                status = HttpStatusCode.Unauthorized,
                message = "failed signing in",
                data = null
            )

        val data = mapOf("token" to tokensData, "user" to userData.data)

        defaultOkResponse(message = "signed in successfully", data = data)

    }

    suspend fun signUp(
        call: ApplicationCall, issuer: String, audience: String
    ): ServerResponse<Any?> = safeTransaction(error = "failed signing up") {

        val request = call.receive<AuthRequest>()

        if (request.email.isNullOrBlank() or request.password.isNullOrBlank())
            return@safeTransaction defaultBadRequestResponse(message = "Email or Password cannot be blank")

        if (request.email.isValidEmail().not()) return@safeTransaction defaultBadRequestResponse(
            message = "invalid email address"
        )

        if (request.password.isValidPassword().not()) return@safeTransaction defaultBadRequestResponse(
            message = "Password must be a minimum of 8 characters containing Uppercase, Lowercase, Number and Special Character"
        )

        val response = usersDao.getUserByEmail(request.email!!)

        if (response.data != null) return@safeTransaction defaultBadRequestResponse(
            message = "email already exists. sign in to continue"
        )

        val hashPassword = PasswordProvider.generatePasswordHash(password = request.password!!)

        val createResponse = usersDao.create(email = request.email, hash = hashPassword)

        if (createResponse.status.isSuccess().not()) return@safeTransaction ServerResponse(
            status = HttpStatusCode.InternalServerError,
            message = "failed signing up",
            data = null
        )

        val user = createResponse.data as User

        val tokensData = TokenProvider.generateTokens(
            issuer = issuer, audience = audience, userId = user.id!!.toString()
        )

        val refreshTokenExpiry =
            TokenProvider.getTokenExpiryDate(issuer = issuer, audience = audience, token = tokensData.refreshToken)

        tokensDao.save(
            token = tokensData.refreshToken,
            userId = user.id,
            expiry = refreshTokenExpiry.toDateTimeString()!!
        )

        val userData = usersDao.getUserDetails(userId = user.id)

        if (userData.status.isSuccess().not())
            return@safeTransaction ServerResponse(
                status = HttpStatusCode.InternalServerError,
                message = "failed getting user details. sign in to continue",
                data = null
            )

        val data = mapOf("token" to tokensData, "user" to userData.data)

        defaultOkResponse(message = "signed up successfully", data = data)

    }

    suspend fun refreshToken(
        call: ApplicationCall, issuer: String, audience: String
    ): ServerResponse<Any?> = safeTransaction(
        error = "failed getting refresh token"
    ) {

        val request = call.receive<RefreshTokenRequest>()
        val token = request.token

        if (token.isNullOrBlank()) return@safeTransaction defaultBadRequestResponse(message = "Invalid refresh token")

        val id = TokenProvider.verify(issuer = issuer, audience = audience, token = token)
            ?: return@safeTransaction defaultBadRequestResponse(message = "Refresh token could not be verified")

        val userId = id.asUUID() ?: return@safeTransaction defaultBadRequestResponse(message = "Invalid refresh token")

        val isNotRefreshToken = TokenProvider.getTokenType(issuer, audience, token) != "refresh"

        if (isNotRefreshToken) return@safeTransaction defaultBadRequestResponse(message = "Invalid refresh token")

        if (tokensDao.exists(userId = userId, token = token).not())
            return@safeTransaction defaultBadRequestResponse(message = "Refresh token has expired sing in to continue")

        tokensDao.delete(token)

        tokensDao.deleteExpiredTokens(userId = userId)

        val tokens = TokenProvider.generateTokens(issuer = issuer, audience = audience, userId = id)

        val expiry = TokenProvider.getTokenExpiryDate(issuer = issuer, audience = audience, token = tokens.refreshToken)

        tokensDao.save(token = tokens.refreshToken, userId = userId, expiry = expiry.toDateTimeString()!!)

        defaultOkResponse(message = "tokens successfully regenerated", tokens)

    }

    suspend fun resetPassword(
        call: ApplicationCall, issuer: String, audience: String
    ): ServerResponse<Any?> = safeTransaction(
        error = "failed resetting password"
    ) {

        val request = call.receive<ResetRequest>()
        val email = request.email

        if (email.isNullOrBlank() || email.isValidEmail().not())
            return@safeTransaction defaultBadRequestResponse(message = "invalid email address")

        /**
         * TODO: implement reset password
         *  1. verify user with email exists
         *  1.a if user with email doesn't exist return error
         *  1.b if user with email exists return true
         *  2. generate random 8 strong digit password
         *  3. send password to user email
         *  4. respond with either success or failure
         */

        defaultOkResponse(message = "still in early stages")

    }

}