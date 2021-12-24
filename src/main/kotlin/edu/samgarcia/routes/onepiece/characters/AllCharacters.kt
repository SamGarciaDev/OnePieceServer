package edu.samgarcia.routes.onepiece.characters

import edu.samgarcia.StringsXML
import edu.samgarcia.models.ApiResponse
import edu.samgarcia.repository.OPCharacterService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

fun Route.getAllCharacters() {
    val characterService: OPCharacterService by inject()

    get("/onepiece/characters") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..characterService.getNumPages())

            val apiResponse = characterService.getPage(page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(false, StringsXML.ALL_CHARACTERS_INVALID_PAGE_QUERY_MSG),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(false, StringsXML.ALL_CHARACTERS_EMPTY_PAGE_MSG),
                status = HttpStatusCode.BadRequest
            )
        }
    }
}