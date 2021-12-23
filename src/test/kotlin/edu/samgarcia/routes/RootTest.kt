package edu.samgarcia

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun accessRootEndpoint() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(method = HttpMethod.Get, uri = "/").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status()
                )
                assertEquals(
                    expected = StringsXML.ROOT_ENDPOINT_MSG,
                    actual = response.content
                )
            }
        }
    }
}