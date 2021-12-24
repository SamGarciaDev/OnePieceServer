package edu.samgarcia.repository

import edu.samgarcia.models.ApiResponse
import edu.samgarcia.models.Character

interface CharacterRepository {
    suspend fun getPage(page: Int = 1) : ApiResponse
    suspend fun searchCharacters(name: String?) : ApiResponse

    fun getAllCharacters(): List<Character>
    fun getCharactersOnPage(pageNumber: Int) : List<Character>
    fun getNumPages() : Int
    fun getNumCharacters() : Int
}