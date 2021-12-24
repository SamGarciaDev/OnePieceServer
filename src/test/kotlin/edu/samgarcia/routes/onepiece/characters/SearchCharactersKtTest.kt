package edu.samgarcia.routes.onepiece.characters

import edu.samgarcia.StringsXML
import edu.samgarcia.models.ApiResponse
import edu.samgarcia.module
import edu.samgarcia.repository.OPCharacterRepo
import edu.samgarcia.repository.OPCharacterRepoImpl
import edu.samgarcia.repository.OPCharacterService
import edu.samgarcia.repository.OPCharacterServiceImpl
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import kotlin.streams.toList
import kotlin.test.Test


class SearchCharactersKtTest : KoinTest {
    private val repo: OPCharacterRepo by inject()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single<OPCharacterRepo> { OPCharacterRepoImpl() }
                single<OPCharacterService> { OPCharacterServiceImpl(get()) }
            }
        )
    }

    @Test
    fun `access character search endpoint, query all characters, verify correct response`() {
        withTestApplication(moduleFunction = Application::module) {
            for (character in repo.getAll()) {
                handleRequest(method = HttpMethod.Get, uri = "/onepiece/characters/search?name=${character.name}").apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                    val expected = ApiResponse(
                        success = true,
                        message = StringsXML.OK,
                        characters = repo.getAll().stream()
                            .filter { c -> c.name.lowercase().contains(character.name.lowercase()) }
                            .toList()
                    )

                    val result = Json.decodeFromString<ApiResponse>(response.content.toString())

                    assertThat(result).isEqualTo(expected)
                }
            }
        }
    }

    @Test
    fun `access character search endpoint, no query, verify correct response`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(method = HttpMethod.Get, uri = "/onepiece/characters/search").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                val expected = ApiResponse(
                    success = true,
                    message = StringsXML.OK,
                    characters = emptyList()
                )

                val result = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertThat(result).isEqualTo(expected)
            }
        }
    }
}