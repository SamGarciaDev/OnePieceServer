package edu.samgarcia.plugins

import edu.samgarcia.routes.onepiece.characters.getAllCharacters
import edu.samgarcia.routes.root
import edu.samgarcia.routes.onepiece.characters.searchCharacters
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        root()
        getAllCharacters()
        searchCharacters()

        static("/images") {
            resources("images")
        }
    }
}
