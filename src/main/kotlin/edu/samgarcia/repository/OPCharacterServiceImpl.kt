package edu.samgarcia.repository

import edu.samgarcia.Strings
import edu.samgarcia.models.ApiResponse
import edu.samgarcia.models.OPCharacter
import kotlin.math.ceil
import kotlin.streams.toList

class OPCharacterServiceImpl(private val repo: OPCharacterRepo): OPCharacterService {

    companion object {
        const val CHARACTERS_PER_PAGE = 3
        const val PREV_PAGE_KEY = "prevPage"
        const val NEXT_PAGE_KEY = "nextPage"
    }

    override suspend fun getPage(page: Int): ApiResponse {
        return ApiResponse(
            success = true,
            message = Strings.OK,
            prevPage = calculatePage(page)[PREV_PAGE_KEY],
            nextPage = calculatePage(page)[NEXT_PAGE_KEY],
            characters = pagination[page] ?: emptyList(),
            lastUpdated = System.currentTimeMillis()
        )
    }

    override suspend fun searchCharacters(name: String?): ApiResponse {
        return ApiResponse(
            success = true,
            message = Strings.OK,
            characters = findCharacters(name)
        )
    }

    override fun getNumPages() : Int = ceil(repo.getAll().size.toDouble() / CHARACTERS_PER_PAGE).toInt()

    private val pagination: Map<Int, List<OPCharacter>> by lazy {
        val characters = repo.getAll()
        val map = mutableMapOf<Int, ArrayList<OPCharacter>>()

        for (i in 1 .. getNumPages()) {
            map[i] = arrayListOf()
        }

        for (i in characters.indices) {
            map[i/CHARACTERS_PER_PAGE + 1]?.add(characters[i])
        }

        map
    }

    private fun findCharacters(name: String?) : List<OPCharacter> {
        if (name.isNullOrBlank()) return emptyList()
        val characters = repo.getAll()

        return characters.stream()
            .filter { character -> character.name.lowercase().contains(name.lowercase()) }
            .toList()
    }

    private fun calculatePage(page: Int) : Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page

        val numPages = getNumPages()

        if (numPages <= 1) return mapOf(
            PREV_PAGE_KEY to null,
            NEXT_PAGE_KEY to null
        )

        if (page in 2..numPages)
            prevPage = prevPage?.minus(1)
        if (page in 1..2)
            nextPage = nextPage?.plus(1)
        if (page <= 1)
            prevPage = null
        if (page == numPages)
            nextPage = null

        return mapOf(
            PREV_PAGE_KEY to prevPage,
            NEXT_PAGE_KEY to nextPage
        )
    }


}