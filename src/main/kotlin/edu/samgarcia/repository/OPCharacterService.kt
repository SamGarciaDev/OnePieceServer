package edu.samgarcia.repository

import edu.samgarcia.models.ApiResponse

interface OPCharacterService {
    suspend fun getPage(page: Int = 1) : ApiResponse
    suspend fun searchCharacters(name: String?) : ApiResponse
    fun getNumPages() : Int
}