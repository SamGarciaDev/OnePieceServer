package edu.samgarcia.routes

import edu.samgarcia.Strings
import edu.samgarcia.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class RootTest {
    @Test
    fun `access root endpoint, verify correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(method = HttpMethod.Get, uri = "/").apply {
                assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
                assertThat(response.content).isEqualTo(Strings.ROOT_ENDPOINT_MSG)
            }
        }
    }
}