package edu.samgarcia

import io.ktor.application.*
import edu.samgarcia.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureKoin()
    configureDefaultHeader()
    configureStatusPages()
}
