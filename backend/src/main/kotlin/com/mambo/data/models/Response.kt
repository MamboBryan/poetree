package com.mambo.data.models

import io.ktor.http.*

data class Response<T>(
    val success: Boolean, val message: String, val data: T
)

data class ServerResponse<T>(
    val status: HttpStatusCode, val message: String, val data: T
)