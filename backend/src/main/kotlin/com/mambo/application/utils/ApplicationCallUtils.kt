package com.mambo.application.utils

import com.mambo.data.models.Response
import com.mambo.data.models.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

suspend fun ApplicationCall.defaultResponse(status: HttpStatusCode, message: String = "Error") {
    return this.respond(
        status = status, message = Response(success = status.isSuccess(), message = message, data = null)
    )
}

suspend fun <T> ApplicationCall.successWithData(status: HttpStatusCode, message: String = "Error", data: T) {
    return this.respond(
        status = status, message = Response(success = status.isSuccess(), message = message, data = data)
    )
}

suspend fun <T> ApplicationCall.respond(response: ServerResponse<T>) {
    return this.respond(
        status = response.status,
        message = Response(success = response.status.isSuccess(), message = response.message, data = response.data)
    )
}

suspend fun ApplicationCall.redirectInternally(path: String) {
    val cp = object : RequestConnectionPoint by this.request.local {
        override val uri: String = path
    }
    val req = object : ApplicationRequest by this.request {
        override val local: RequestConnectionPoint = cp
    }
    val call = object : ApplicationCall by this {
        override val request: ApplicationRequest = req
    }

    this.application.execute(call, Unit)
}

fun ApplicationCall.getCurrentUserId(): UUID? {
    val principal = this.principal<JWTPrincipal>() ?: return null
    val idString = principal.payload.getClaim("userId")?.asString() ?: return null
    return UUID.fromString(idString) ?: return null
}

fun ApplicationCall.getQuery(queryName: String): String? {
    return this.request.queryParameters[queryName]
}

fun ApplicationCall.getUrlParameter(parameterName: String): String? {
    return this.parameters[parameterName]
}