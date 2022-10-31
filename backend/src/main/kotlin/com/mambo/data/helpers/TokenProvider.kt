package com.mambo.data.helpers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

object TokenProvider {

    private val secret = System.getenv("SECRET_KEY")
    private val algorithm = Algorithm.HMAC512(secret)

    private const val ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 10L
    private const val REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7L

    private fun expiresAt(time: Long) = Date(System.currentTimeMillis() + time)

    fun verifier(audience: String, issuer: String) = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun validateToken(credential: JWTCredential): Principal? {
        val userId = credential.payload.getClaim("userId").asString()
        val type = credential.payload.getClaim("type").asString()
        return when (userId != null && type.equals("access")) {
            true -> JWTPrincipal(credential.payload)
            else -> null
        }
    }

    fun generateTokens(issuer: String, audience: String, userId: String): TokenResponse {
        val access = createAccessToken(issuer, audience, userId)
        val refresh = createRefreshToken(issuer, audience, userId)
        return TokenResponse(accessToken = access, refreshToken = refresh)
    }

    private fun createAccessToken(issuer: String, audience: String, userId: String) = JWT.create()
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("userId", userId)
        .withClaim("type", "access")
        .withExpiresAt(expiresAt(time = ACCESS_TOKEN_VALIDITY))
        .sign(algorithm)

    private fun createRefreshToken(issuer: String, audience: String, userId: String) = JWT.create()
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("userId", userId)
        .withClaim("type", "refresh")
        .withExpiresAt(expiresAt(time = REFRESH_TOKEN_VALIDITY))
        .sign(algorithm)

    fun getUserId(issuer: String, audience: String, token: String) =
        verifier(issuer = issuer, audience = audience)
            .verify(token)
            .claims["userId"]
            ?.asString()

    fun verify(issuer: String, audience: String, token: String) =
        verifier(issuer = issuer, audience = audience)
            .verify(token)
            .claims["userId"]
            ?.asString()

    fun getTokenType(issuer: String, audience: String, token: String) =
        verifier(issuer = issuer, audience = audience)
            .verify(token)
            .claims["type"]
            ?.asString()

    fun getTokenExpiryDate(issuer: String, audience: String, token: String) =
        verifier(issuer = issuer, audience = audience)
            .verify(token)
            .expiresAt
            .time

}