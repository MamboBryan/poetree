package com.mambo.application.utils

import com.mambo.data.models.ServerResponse
import io.ktor.http.*

fun defaultOkResponse(message: String = "success", data: Any? = null) = ServerResponse(
    status = HttpStatusCode.OK, message = message, data = data
)

fun defaultCreatedResponse(message: String = "success", data: Any? = null) = ServerResponse(
    status = HttpStatusCode.Created, message = message, data = data
)

fun defaultBadRequestResponse(message: String = "Bad Request",data: Any? = null) = ServerResponse(
    status = HttpStatusCode.BadRequest, message = message, data = data
)

fun defaultNotFoundResponse(message: String = "Bad Request",data: Any? = null) = ServerResponse(
    status = HttpStatusCode.NotFound, message = message, data = data
)

fun serverErrorResponse(message: String = "error", data: Any? = null) = ServerResponse(
    status = HttpStatusCode.InternalServerError, message = message, data = data
)
