package edu.samgarcia.routes

import edu.samgarcia.models.ApiResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

fun Route.getAllCharacters() {
    get("/onepiece/characters") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)
            call.respond(page)
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(false, "Only numbers allowed for page query."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(false, "Characters not found."),
                status = HttpStatusCode.BadRequest
            )
        }
    }
}