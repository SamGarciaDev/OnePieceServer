package edu.samgarcia.routes

import edu.samgarcia.StringsXML
import io.ktor.server.testing.*
import kotlin.test.Test
import edu.samgarcia.module
import io.ktor.application.*
import io.ktor.http.*
import org.assertj.core.api.Assertions.assertThat

class PageNotFoundTest {
    @Test
    fun `access non existent endpoint, verify correct response`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(method = HttpMethod.Get, uri = "/404").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.NotFound)
                assertThat(response.content).isEqualTo(StringsXML.PAGE_NOT_FOUND)
            }
        }
    }
}