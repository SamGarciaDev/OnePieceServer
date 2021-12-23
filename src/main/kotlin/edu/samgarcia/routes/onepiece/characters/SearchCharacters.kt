package edu.samgarcia.routes

import edu.samgarcia.repository.CharacterRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.searchHeroes() {
    val characterRepo: CharacterRepository by inject()

    get("/onepiece/characters/search") {
        val name = call.request.queryParameters["name"]
        val apiResponse = characterRepo.searchHeroes(name)

        call.respond(
            message = apiResponse,
            status = HttpStatusCode.OK
        )
    }
}