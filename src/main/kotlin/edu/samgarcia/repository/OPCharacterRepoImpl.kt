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

    object  OPChar : Table() {
        val id = integer("id")
        val name = text("name")
        val img = text("img")
        val about = text("about")
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
                        rating = row[OPChar.rating].toDouble(),
                        devilFruits = Json.decodeFromString(row[OPChar.devilFruits]),
                        family = Json.decodeFromString(row[OPChar.family])
                    )
                )
            }
        }

        chars
    }
}