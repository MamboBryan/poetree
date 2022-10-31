package com.mambo.application

import com.mambo.application.plugins.*
import com.mambo.data.DatabaseFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    Napier.base(DebugAntilog())
    DatabaseFactory.init()

    configureSecurity()
    configureRouting()
    configureSerialization()

}
