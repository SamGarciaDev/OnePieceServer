package edu.samgarcia.repository

import edu.samgarcia.models.ApiResponse
import edu.samgarcia.models.Character

class CharacterRepositoryImpl: CharacterRepository {
    override val characters: Map<Int, List<Character>>
        get() = TODO("Not yet implemented")
    override val page1: List<Character>
        get() = TODO("Not yet implemented")
    override val page2: List<Character>
        get() = TODO("Not yet implemented")
    override val page3: List<Character>
        get() = TODO("Not yet implemented")
    override val page4: List<Character>
        get() = TODO("Not yet implemented")
    override val page5: List<Character>
        get() = TODO("Not yet implemented")

    override suspend fun getAllCharacters(page: Int): ApiResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchHeroes(name: String): ApiResponse {
        TODO("Not yet implemented")
    }
}