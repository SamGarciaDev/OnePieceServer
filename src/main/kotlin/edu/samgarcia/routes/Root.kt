package edu.samgarcia.routes

import edu.samgarcia.StringsXML
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.root() {
    get("/") {
        call.respond(
            message = StringsXML.ROOT_ENDPOINT_MSG,
            status = HttpStatusCode.OK
        )
    }
}