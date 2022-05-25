package edu.samgarcia.repository

import edu.samgarcia.models.OPCharacter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OPCharacterRepoImpl : OPCharacterRepo {
    override fun getAll(): List<OPCharacter> {
        return characters
    }

    object OPChar : Table() {
        val id = integer("id")
        val name = text("name")
        val img = text("img")
        val about = text("about")
        val origin = text("origin")
        val bounty = long("bounty")
        val rating = float("rating")
        val devilFruits = text("devilFruits")
        val family = text("family")
    }

    private val characters: List<OPCharacter> by lazy {
        val chars = arrayListOf<OPCharacter>()

        Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres",
            user = "user",
            password = "123"
        )

        transaction {
            OPChar.selectAll().forEach { row ->
                chars.add(
                    OPCharacter(
                        id = row[OPChar.id],
                        name = row[OPChar.name],
                        img = row[OPChar.img],
                        about = row[OPChar.about],
                        origin = row[OPChar.origin],
                        bounty = row[OPChar.bounty],
                        rating = row[OPChar.rating].toDouble(),
                        devilFruits = Json.decodeFromString(row[OPChar.devilFruits]),
                        family = Json.decodeFromString(row[OPChar.family])
                    )
                )
            }
        }

        chars
    }

    override fun initdb() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres",
            user = "user",
            password = "123"
        )

        val characters = listOf(
            OPCharacter(
                id = 1,
                name = "Monkey D. Luffy",
                img = "/images/luffy.jpeg",
                about = "Monkey D. Luffy, also known as \" Straw Hat Luffy\" and commonly as \"Straw Hat\", is the main " +
                        "protagonist of the manga and anime, One Piece.",
                origin = "East Blue",
                bounty = 1500000000,
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
            OPCharacter(
                id = 2,
                name = "Portgas D. Ace",
                img = "/images/ace.png",
                about = "Portgas D. \"Fire Fist\" Ace was the second division commander of the Whitebeard Pirates and " +
                        "Luffy's older brother.",
                rating = 4.2,
                origin = "South Blue",
                bounty = 550000000,
                devilFruits = listOf(
                    "Mera Mera no Mi"
                ),
                family = listOf(
                    "Monkey D. Garp",
                    "Monkey D. Dragon",
                    "Monkey D. Luffy",
                    "Sabo"
                )
            ),
            OPCharacter(
                id = 3,
                name = "Marshall D. Teach",
                img = "/images/blackbeard.png",
                about = "Marshall D. Teach, most commonly referred to by his epithet Blackbeard, is the " +
                        "captain-turned-admiral of the Blackbeard Pirates, the captain of their tenth ship, and " +
                        "currently one of the Four Emperors.",
                rating = 2.7,
                origin = "Grand Line",
                bounty = 2247600000,
                devilFruits = listOf(
                    "Yami Yami no Mi",
                    "Gura Gura no Mi"
                ),
                family = emptyList()
            ),
            OPCharacter(
                id = 4,
                name = "Kaidou",
                img = "/images/kaidou.png",
                about = "Kaidou of the Beasts, renowned as the world's \"Strongest Creature\", is the " +
                        "Governor-General of the Beasts Pirates and one of the Four Emperors ruling over the " +
                        "New World.",
                rating = 3.6,
                origin = "Grand Line",
                bounty = 4611100000,
                devilFruits = listOf(
                    "Uo Uo no Mi"
                ),
                family = listOf(
                    "Yamato"
                )
            ),
            OPCharacter(
                id = 5,
                name = "Trafalgar D. Water Law",
                img = "/images/law.jpg",
                about = "Trafalgar D. Water Law, known by his epithet as the \"Surgeon of Death\", is a pirate from " +
                        "North Blue and the captain and doctor of the Heart Pirates.",
                rating = 3.2,
                origin = "North Blue",
                bounty = 500000000,
                devilFruits = listOf(
                    "Ope Ope no Mi"
                ),
                family = emptyList()
            ),
            OPCharacter(
                id = 6,
                name = "Roronoa Zoro",
                img = "/images/zoro.jpg",
                about = "Roronoa Zoro, also known as \"Pirate Hunter\" Zoro, is the combatant of the Straw Hat Pirates, " +
                        "and one of their two swordsmen.",
                rating = 4.5,
                origin = "East Blue",
                bounty = 320000000,
                devilFruits = emptyList(),
                family = emptyList()
            ),
            OPCharacter(
                id = 7,
                name = "Nico Robin",
                img = "/images/robin.jpg",
                about = "Nico Robin, also known by her epithet \"Devil Child\" and the \"Light of the Revolution\", is " +
                        "the archaeologist of the Straw Hat Pirates.",
                rating = 2.2,
                origin = "West Blue",
                bounty = 130000000,
                devilFruits = listOf(
                    "Hana Hana no Mi"
                ),
                family = emptyList()
            ),
            OPCharacter(
                id = 8,
                name = "Bellamy",
                img = "/images/bellamy.jpeg",
                about = "Bellamy the Hyena is the former captain of the Bellamy Pirates, and a former member of the " +
                        "Donquixote Pirates. After the Dressrosa Arc, he retired from piracy and became a dyer.",
                rating = 1.0,
                origin = "Grand Line",
                bounty = 195000000,
                devilFruits = listOf(
                    "Bane Bane no Mi"
                ),
                family = emptyList()
            ),
            OPCharacter(
                id = 9,
                name = "Brook",
                img = "/images/brook.jpeg",
                about = "\"Soul King\" Brook is the musician of the Straw Hat Pirates, and one of their two swordsmen.",
                rating = 3.9,
                origin = "West Blue",
                bounty = 83000000,
                devilFruits = listOf(
                    "Yomi Yomi no Mi"
                ),
                family = emptyList()
            )
        )

        transaction {
            characters.forEach { character ->
                OPChar.insert {
                    it[id] = character.id
                    it[name] = character.name
                    it[img] = character.img
                    it[about] = character.about
                    it[origin] = character.origin
                    it[bounty] = character.bounty
                    it[rating] = character.rating.toFloat()

                    var fruits = "["
                    character.devilFruits.forEachIndexed { i, f ->
                        fruits += "\"${f}\""
                        if (i < character.devilFruits.size - 1) fruits += ","
                    }
                    fruits += "]"

                    it[devilFruits] = fruits

                    var fam = "["
                    character.family.forEachIndexed { i, f ->
                        fam += "\"${f}\""
                        if (i < character.family.size - 1) fam += ","
                    }
                    fam += "]"

                    it[family] = fam
                }
            }
        }
    }
}