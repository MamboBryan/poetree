package com.mambo.application.plugins

import com.mambo.data.models.Something
import com.mambo.application.routes.*
import com.mambo.application.utils.defaultOkResponse
import com.mambo.application.utils.respond
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    routing {

        route("v1") {

            get {
                val response = defaultOkResponse(message = "dang! you found me.", data = Something.default)
                call.respond(response)
            }

            get("/") {
                val response = defaultOkResponse(message = "who told you about this?", data = Something.alternate)
                call.respond(response)
            }

            authRoutes(issuer = issuer, audience = audience)

            authenticate("auth-jwt") {

                userRoutes(issuer = issuer, audience = audience)
                topicRoutes()
                poemRoutes()
                commentRoutes()

            }

        }

    }

}
