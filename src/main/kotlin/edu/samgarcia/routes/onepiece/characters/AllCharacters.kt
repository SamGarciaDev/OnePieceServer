package edu.samgarcia.routes

import edu.samgarcia.models.ApiResponse
import edu.samgarcia.repository.CharacterRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

fun Route.getAllCharacters() {
    val characterRepo: CharacterRepository by inject()

    get("/onepiece/characters") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..characterRepo.getNumPages())
            val apiResponse = characterRepo.getCharactersOnPage(page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(false, "Only numbers allowed for page query. 💥"),
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