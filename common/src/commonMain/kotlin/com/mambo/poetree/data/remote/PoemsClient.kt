package com.mambo.poetree.data.remote

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class PoemsClient {

    fun client() = HttpClient {

//        engine {
//            this.addInterceptor(networkInterceptor)
//            this.addInterceptor(authInterceptor)
//            this.addInterceptor(loggingInterceptor)
//        }

        install(ContentNegotiation) {
            json(json = Json {
                encodeDefaults = false
                explicitNulls = false
            })
        }

        install(Logging) {
            level = LogLevel.BODY
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i(message)
                }
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

    }

}