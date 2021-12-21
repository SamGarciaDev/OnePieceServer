package edu.samgarcia.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.root() {
    get("/") {
        call.respond(
            message = "Welcome to the One Piece API 👒",
            status = HttpStatusCode.OK
        )
    }
}