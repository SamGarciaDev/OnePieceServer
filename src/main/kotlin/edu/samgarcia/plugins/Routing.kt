package edu.samgarcia.plugins

import edu.samgarcia.routes.getAllCharacters
import edu.samgarcia.routes.root
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {
    routing {
        root()
        getAllCharacters()
    }
}
