package edu.samgarcia.routes.onepiece.characters

import edu.samgarcia.Strings
import edu.samgarcia.models.ApiResponse
import edu.samgarcia.module
import edu.samgarcia.repository.OPCharacterRepo
import edu.samgarcia.repository.OPCharacterService
import edu.samgarcia.repository.OPCharacterServiceImpl
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.assertj.core.api.Assertions.assertThat
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test


internal class AllCharactersKtTest : KoinTest {
    private val repo: OPCharacterRepo by inject()
    private val characterService: OPCharacterService by inject()

    @Suppress("KotlinConstantConditions")
    @Test
    fun `access all characters endpoint, verify correct response`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(
                method = HttpMethod.Get,
                uri = "/onepiece/characters"
            ).apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                val expectedPage = 1

                val expected = ApiResponse(
                    success = true,
                    message = Strings.OK,
                    prevPage = if (expectedPage - 1 < 1) null else expectedPage - 1,
                    nextPage = if (expectedPage + 1 > characterService.getNumPages()) null else expectedPage + 1,
                    characters = repo.getAll().subList(0, OPCharacterServiceImpl.CHARACTERS_PER_PAGE)
                )

                val result = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertThat(result).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `access all characters endpoint, query all pages, verify correct response`() {
        withTestApplication(moduleFunction = Application::module) {
            for (page in 1..characterService.getNumPages()) {
                handleRequest(
                    method = HttpMethod.Get,
                    uri = "/onepiece/characters?page=$page"
                ).apply {
                    assertThat(response.status()).isEqualTo(HttpStatusCode.OK)

                    val charsPerPage = OPCharacterServiceImpl.CHARACTERS_PER_PAGE

                    val result = Json.decodeFromString<ApiResponse>(response.content.toString())

                    val expected = ApiResponse(
                        success = true,
                        message = Strings.OK,
                        prevPage = if (page - 1 < 1) null else page - 1,
                        nextPage = if (page + 1 > characterService.getNumPages()) null else page + 1,
                        characters = repo.getAll().subList(
                            fromIndex = (page - 1) * charsPerPage,
                            toIndex = (page - 1) * charsPerPage + charsPerPage
                        ),
                        lastUpdated = result.lastUpdated
                    )


                    assertThat(result).isEqualTo(expected)
                }
            }
        }
    }

    @Test
    fun `access all characters endpoint, query out of bounds page number, verify correct response`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(
                method = HttpMethod.Get,
                uri = "/onepiece/characters?page=7"
            ).apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)

                val expected = ApiResponse(
                    success = false,
                    message = Strings.ALL_CHARACTERS_EMPTY_PAGE_MSG
                )

                val result = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertThat(result).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `access all characters endpoint, query invalid page number, verify correct response`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(
                method = HttpMethod.Get,
                uri = "/onepiece/characters?page=asd"
            ).apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)

                val expected = ApiResponse(
                    success = false,
                    message = Strings.ALL_CHARACTERS_INVALID_PAGE_QUERY_MSG
                )

                val result = Json.decodeFromString<ApiResponse>(response.content.toString())

                assertThat(result).isEqualTo(expected)
            }
        }
    }
}