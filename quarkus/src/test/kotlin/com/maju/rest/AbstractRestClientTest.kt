package com.maju.rest

import com.google.gson.Gson
import com.maju.rest.client.IRestClient
import com.maju.rest.client.RestClient
import com.maju.rest.response.RestResponse
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

abstract class AbstractRestClientTest {
    private val port = randomFrom()

    var mockServer: MockServerClient = MockServerClient("localhost", port)
    private val gson = Gson()
    protected val getPath = "/home"
    protected val getPathWithBasicAuthorization = "/secured/basic"
    protected val getPathWithBearerAuthorization = "/secured/bearer"
    protected val postPathWithBody = "/product"
    protected val postPathWithoutBody = "/check"
    protected val restParam: Pair<String, String> = "name" to "oberstrike"
    private val restClient = RestClient.create(port)

    protected fun getRestClient(): IRestClient {
        return restClient
    }

    protected fun getProduct(id: Int) = Product(id, "Aple")

    protected fun assertThatRequestWasSuccessfull(response: RestResponse) {
        val statusCode = response.statusCode
        assert(statusCode == 200)

    }

    open fun prepare() {
        mockServer = ClientAndServer.startClientAndServer(port)

        //Post-Request  with
        mockServer.given(
            HttpRequest.request()
                .withMethod("POST")
                .withPath(postPathWithoutBody)
                .withBody("name=oberstrike")
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
        )

        //Post request
        mockServer.given(
            HttpRequest.request()
                .withMethod("POST")
                .withPath(postPathWithBody)
                .withBody(toJson(getProduct(0)))
        ).respond(
            HttpResponse.response()
                .withBody(toJson(getProduct(1)))
                .withStatusCode(200)
        )

        //Get-Request with query string
        mockServer.given(
            HttpRequest.request()
                .withMethod("GET")
                .withPath(getPath)
                .withQueryStringParameter("name", "oberstrike")
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
        )

        //Get-Request with basic-authorization
        mockServer.given(
            HttpRequest.request()
                .withMethod("GET")
                .withHeader("Authorization", "Basic dGVzdDp0ZXN0")
                .withPath(getPathWithBasicAuthorization)
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
        )

        //Get-Request with basic-authorization
        mockServer.given(
            HttpRequest.request()
                .withMethod("GET")
                .withHeader("Authorization", "Bearer token")
                .withPath(getPathWithBearerAuthorization)
        ).respond(
            HttpResponse.response()
                .withStatusCode(200)
        )

    }

    open fun teardown() {
        mockServer.close()
    }

    protected fun toJson(product: Product) = gson.toJson(product)


    data class Product(
        val id: Int,
        val name: String
    )
}