package edu.samgarcia.routes.onepiece.characters

import edu.samgarcia.repository.OPCharacterService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.searchCharacters() {
    val characterService: OPCharacterService by inject()

    get("/onepiece/characters/search") {
        val name = call.request.queryParameters["name"]
        val apiResponse = characterService.searchCharacters(name)

        call.respond(
            message = apiResponse,
            status = HttpStatusCode.OK
        )
    }
}