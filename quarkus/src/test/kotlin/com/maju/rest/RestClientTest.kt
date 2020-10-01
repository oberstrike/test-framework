package com.maju.rest

import com.google.gson.Gson
import com.maju.rest.client.IRestClient
import com.maju.rest.client.RestClient
import com.maju.rest.request.RestRequestFactory
import com.maju.rest.request.get.get
import com.maju.rest.request.post.post
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
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
    private val gson = Gson()
    private val getPath = "/home"
    private val postPathWithBody = "/product"
    private val postPathWithoutBody = "/check"
    private val restParam: Pair<String, String> = "name" to "oberstrike"

    @BeforeAll
    fun prepare() {
        mockServer = ClientAndServer.startClientAndServer(port)

        mockServer.given(
            request()
                .withMethod("POST")
                .withPath(postPathWithoutBody)
                .withBody("name=oberstrike")
        ).respond(
            response()
                .withStatusCode(200)
        )

        mockServer.given(
            request()
                .withMethod("POST")
                .withPath(postPathWithBody)
                .withBody(toJson(getProduct(0)))
        ).respond(
            response()
                .withBody(toJson(getProduct(1)))
                .withStatusCode(200)
        )

        mockServer.given(
            request()
                .withMethod("GET")
                .withPath(getPath)
                .withQueryStringParameter("name", "oberstrike")
        ).respond(
            response()
                .withStatusCode(200)
        )


    }

    @Test
    fun standardGetTest() {
        val client = createRestClient()

        val request = RestRequestFactory.get {
            path(getPath)
            params(mapOf(restParam))
        }


        val response = client.get(request)
        val statusCode = response.statusCode
        assert(statusCode == 200)
    }

    @Test
    fun standardPostWithoutBodyTest() {
        val client = createRestClient()

        val request = RestRequestFactory.post {
            path(postPathWithoutBody)
            param(restParam)
        }


        val response = client.post(request)
        val statusCode = response.statusCode

        assert(statusCode == 200)
    }


    @Test
    fun standardPostWithBodyTest() {
        val client = createRestClient()

        val request = RestRequestFactory.post {
            path(postPathWithBody)
            body(toJson(getProduct(0)))
            param(restParam)
        }

        val response = client.post(request)
        val statusCode = response.statusCode
        val body = response.body

        assert(statusCode == 200)
        assert(body == toJson(getProduct(1)))
    }

    private fun toJson(product: Product) = gson.toJson(product)


    @AfterAll
    fun tearDown() {
        mockServer.close()
    }

    private fun createRestClient(): IRestClient {
        return RestClient.create(port)
    }

    private fun getProduct(id: Int) = Product(id, "Aple")


    data class Product(
        val id: Int,
        val name: String
    )
}