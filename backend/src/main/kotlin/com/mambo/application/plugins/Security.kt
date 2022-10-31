package com.mambo.application.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mambo.data.helpers.TokenProvider
import com.mambo.application.utils.defaultResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.*
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val secret = System.getenv("SECRET_KEY")

private val algorithm = Algorithm.HMAC512(secret)

private fun expiresAt(): Date {
    return Date(System.currentTimeMillis() + 36_000_00 * 1)
}

fun hash(password: String): String {
    val key = hex(secret)
    val hmacKey = SecretKeySpec(key, "HmacSHA1")
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}

fun getVerifier(audience: String, issuer: String) = JWT
    .require(algorithm)
    .withAudience(audience)
    .withIssuer(issuer)
    .build()

fun generateToken(issuer: String, audience: String, userId: String): String = JWT
    .create()
    .withAudience(audience)
    .withIssuer(issuer)
    .withExpiresAt(expiresAt())
    .withClaim("id", userId)
    .sign(algorithm)

fun setTokenExpiry(issuer: String, audience: String, keyId: String) = JWT
    .create()
    .withAudience(audience)
    .withIssuer(issuer)
    .withKeyId(keyId)
    .withExpiresAt(Date())
    .sign(algorithm)

fun Application.configureSecurity() {

    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt("auth-jwt") {

            realm = jwtRealm

            verifier(TokenProvider.verifier(audience = jwtAudience, issuer = jwtIssuer))

            validate { credential ->
                return@validate TokenProvider.validateToken(credential = credential)
            }

            challenge { _, _ ->
                return@challenge call.defaultResponse(status = HttpStatusCode.Unauthorized, message = "Unauthorized")
            }

        }
    }

}

