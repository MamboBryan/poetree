package com.mambo.application.utils

import com.mambo.data.models.ServerResponse
import io.github.aakira.napier.Napier
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

suspend fun <T : Any?> safeTransaction(
    error: String = "error",
    block: suspend () -> ServerResponse<T?>
): ServerResponse<T?> {
    return try {
        block()
    } catch (e: Exception) {
        Napier.e(e.localizedMessage, e)
        ServerResponse(status = HttpStatusCode.InternalServerError, message = error, data = null)
    }
}

suspend fun <T> query(block: () -> T): T = withContext(Dispatchers.IO) {
    transaction {
        block.invoke()
    }
}
