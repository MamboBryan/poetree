package com.mambo.application.routes

import com.mambo.application.controllers.AuthController
import com.mambo.application.plugins.generateToken
import com.mambo.application.plugins.hash
import com.mambo.application.utils.defaultResponse
import com.mambo.application.utils.isValidPassword
import com.mambo.application.utils.respond
import com.mambo.application.utils.successWithData
import com.mambo.data.dao.UsersDao
import com.mambo.data.helpers.TokenProvider
import com.mambo.data.requests.AuthRequest
import com.mambo.data.requests.ResetRequest
import com.mambo.data.tables.user.CompleteUserDto
import com.mambo.data.tables.user.User
import com.mambo.data.tables.user.toUserDto
import io.github.aakira.napier.Napier
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.authRoutes(
    issuer: String, audience: String
) {

    val authController = AuthController()

    route("auth") {

        post("signin") {

            val response = authController.signIn(call = call, issuer = issuer, audience = audience)
            return@post call.respond(response)

        }

        post("signup") {

            val response = authController.signUp(call = call, issuer = issuer, audience = audience)
            return@post call.respond(response)

        }

        post("refresh") {

            val response = authController.refreshToken(call = call, issuer = issuer, audience = audience)
            return@post call.respond(response)

        }

        post("reset") {

            val response = authController.resetPassword(call = call, issuer = issuer, audience = audience)
            return@post call.respond(response)

        }

    }

}