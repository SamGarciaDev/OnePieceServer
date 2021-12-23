package edu.samgarcia.repository

import edu.samgarcia.StringsXML
import edu.samgarcia.models.ApiResponse
import edu.samgarcia.models.Character
import kotlin.math.ceil
import kotlin.streams.toList


class CharacterRepositoryImpl: CharacterRepository {
    companion object {
        private const val CHARS_PER_PAGE = 3
        private const val PREV_PAGE_KEY = "prevPage"
        private const val NEXT_PAGE_KEY = "nextPage"
    }

    private val pagination: Map<Int, List<Character>> by lazy {
        val map = mutableMapOf<Int, ArrayList<Character>>()

        for (i in 1 .. getNumPages()) {
            map[i] = arrayListOf()
        }

        for (i in characters.indices) {
            map[i/CHARS_PER_PAGE + 1]!!.add(characters[i])
        }

        map
    }

    private val characters = listOf(
        Character(
            id = 1,
            name = "Monkey D. Luffy",
            img = "/images/luffy.png",
            about = "Monkey D. Luffy, also known as \" Straw Hat Luffy\" and commonly as \"Straw Hat\", is the main " +
                    "protagonist of the manga and anime, One Piece.",
            rating = 5.0,
            devilFruits = listOf(
                "Gomu Gomu no Mi"
            ),
            family = listOf(
                "Monkey D. Garp",
                "Monkey D. Dragon",
                "Portgas D. Aces",
                "Sabo"
            )
        ),
        Character(
            id = 2,
            name = "Portgas D. Ace",
            img = "/images/ace.png",
            about = "Portgas D. \"Fire Fist\" Ace was the second division commander of the Whitebeard Pirates and " +
                    "Luffy's older brother.",
            rating = 5.0,
            devilFruits = listOf(
                "Mera Mera no Mi"
            ),
            family = listOf(
                "Monkey D. Garp",
                "Monkey D. Dragon",
            "Portgas D. Aces",
                "Sabo"
            )
        ),
        Character(
            id = 3,
            name = "Marshall D. Teach",
            img = "/images/blackbeard.png",
            about = "Marshall D. Teach, most commonly referred to by his epithet Blackbeard, is the " +
                    "captain-turned-admiral of the Blackbeard Pirates, the captain of their tenth ship, and " +
                    "currently one of the Four Emperors.",
            rating = 5.0,
            devilFruits = listOf(
                "Yami Yami no Mi",
                "Gura Gura no Mi"
            ),
            family = emptyList()
        ),
        Character(
            id = 4,
            name = "Kaidou",
            img = "/images/kaidou.png",
            about = "Kaidou of the Beasts, renowned as the world's \"Strongest Creature\", is the " +
                    "Governor-General of the Beasts Pirates and one of the Four Emperors ruling over the " +
                    "New World.",
            rating = 5.0,
            devilFruits = listOf(
                "Uo Uo no Mi"
            ),
            family = listOf(
                "Yamato"
            )
        ),
        Character(
            id = 5,
            name = "Trafalgar D. Water Law",
            img = "/images/law.png",
            about = "Trafalgar D. Water Law, known by his epithet as the \"Surgeon of Death\", is a pirate from " +
                    "North Blue and the captain and doctor of the Heart Pirates.",
            rating = 5.0,
            devilFruits = listOf(
                "Ope Ope no Mi"
            ),
            family = emptyList()
        ),
        Character(
            id = 6,
            name = "Roronoa Zoro",
            img = "/images/zoro.png",
            about = "Roronoa Zoro, also known as \"Pirate Hunter\" Zoro, is the combatant of the Straw Hat Pirates, " +
                    "and one of their two swordsmen.",
            rating = 5.0,
            devilFruits = emptyList(),
            family = emptyList()
        ),
        Character(
            id = 7,
            name = "Nico Robin",
            img = "/images/robin.png",
            about = "Nico Robin, also known by her epithet \"Devil Child\" and the \"Light of the Revolution\", is " +
                    "the archaeologist of the Straw Hat Pirates.",
            rating = 5.0,
            devilFruits = listOf(
                "Hana Hana no Mi"
            ),
            family = emptyList()
        ),
        Character(
            id = 8,
            name = "Bellamy",
            img = "/images/bellamy.png",
            about = "Bellamy the Hyena is the former captain of the Bellamy Pirates, and a former member of the " +
                    "Donquixote Pirates. After the Dressrosa Arc, he retired from piracy and became a dyer.",
            rating = 5.0,
            devilFruits = listOf(
                "Bane Bane no Mi"
            ),
            family = emptyList()
        ),
        Character(
            id = 9,
            name = "/images/brook.png",
            img = "https://imgur.com/n3UJZuS",
            about = "\"Soul King\" Brook is the musician of the Straw Hat Pirates, and one of their two swordsmen.",
            rating = 5.0,
            devilFruits = listOf(
                "Yomi Yomi no Mi"
            ),
            family = emptyList()
        )
    )

    override fun getNumPages(): Int {
        return ceil(characters.size/CHARS_PER_PAGE.toDouble()).toInt()
    }

    override suspend fun getCharactersOnPage(page: Int): ApiResponse {
        return ApiResponse(
            success = true,
            message = StringsXML.OK,
            prevPage = calculatePage(page)[PREV_PAGE_KEY],
            nextPage = calculatePage(page)[NEXT_PAGE_KEY],
            characters = pagination[page]!!
        )
    }

    override suspend fun searchHeroes(name: String?): ApiResponse {
        return ApiResponse(
            success = true,
            message = StringsXML.OK,
            characters = findCharacters(name)
        )
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
        if (page == 1)
            prevPage = null
        if (page == numPages)
            nextPage = null

        return mapOf(
            PREV_PAGE_KEY to prevPage,
            NEXT_PAGE_KEY to nextPage
        )
    }

    private fun findCharacters(name: String?) : List<Character> {
        if (name.isNullOrBlank()) return emptyList()

        return characters.stream()
            .filter { character -> character.name.lowercase().contains(name.lowercase()) }
            .toList()
    }
}