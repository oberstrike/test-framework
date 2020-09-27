package com.maju.rest

import org.junit.After
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.matchers.Times
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import java.util.*

val random = Random()
internal fun randomFrom(from: Int = 1024, to: Int = 65535): Int {
    return random.nextInt(to - from) + from
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestClientTest {
    private val port = randomFrom()
    var mockServer: MockServerClient = MockServerClient("localhost", port)
    val url = "http://localhost:$port"


    @BeforeAll
    fun prepare() {
        mockServer = ClientAndServer.startClientAndServer(port)
        mockServer.`when`(
            request()
                .withMethod("GET")
                .withPath("/home")
        ).respond(
            response()
                .withStatusCode(200)
        )
    }

    @Test
    fun test() {
        val client = RestClient.create(port)
        val statusCode = client.get(RestRequestBuilder.create("/home").build()).statusCode

        assert(statusCode == 200)

    }


    @AfterAll
    fun tearDown() {
        mockServer.close()
    }


}