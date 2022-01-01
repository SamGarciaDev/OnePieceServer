package edu.samgarcia.plugins

import edu.samgarcia.Strings
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) {
            call.respond(
                message = Strings.PAGE_NOT_FOUND,
                status = HttpStatusCode.NotFound
            )
        }
    }
}