package edu.samgarcia.repository

import edu.samgarcia.models.ApiResponse
import edu.samgarcia.models.Character

interface CharacterRepository {
    val characters: Map<Int, List<Character>>

    val page1: List<Character>
    val page2: List<Character>
    val page3: List<Character>

    suspend fun getAllCharacters(page: Int = 1) : ApiResponse
    suspend fun searchHeroes(name: String) : ApiResponse
}