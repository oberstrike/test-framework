package com.maju.rest

import com.maju.rest.request.RestRequestFactory
import com.maju.rest.request.auth.RequestAuth
import com.maju.rest.request.get.get
import com.maju.rest.request.post.post
import com.maju.rest.request.put.put
import org.junit.jupiter.api.*
import java.util.*

val random = Random()
internal fun randomFrom(from: Int = 1024, to: Int = 5555): Int {
    return random.nextInt(to - from) + from
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestClientTest : AbstractRestClientTest() {


    @BeforeAll
    override fun prepare() {
        super.prepare()
    }

    @AfterAll
    fun tearDown() {
        super.teardown()
    }

    @Test
    fun standardGetTest() {
        val client = getRestClient()

        val request = RestRequestFactory.get {
            path(getPath)
            params(mapOf(restParam))
        }
        val response = client.send(request)
        assertThatRequestWasSuccessfull(response)
    }

    @Test
    fun basicAuthGetTest_negative() {
        val client = getRestClient()


        val request = RestRequestFactory.get {
            path(getPathWithBasicAuthorization)
        }

        val response = client.send(request)
        assert(response.statusCode != 200)

    }

    @Test
    fun basicAuthGetTest() {
        val client = getRestClient()

        val basicAuth = RequestAuth.basic("test", "test")

        val request = RestRequestFactory.get {
            path(getPathWithBasicAuthorization)
            auth(basicAuth)
        }

        val response = client.send(request)
        assertThatRequestWasSuccessfull(response)

    }


    @Test
    fun standardPostWithoutBodyTest() {
        val client = getRestClient()

        val request = RestRequestFactory.post {
            path(postPathWithoutBody)
            param(restParam)
        }

        val response = client.send(request)
        assertThatRequestWasSuccessfull(response)
    }


    @Test
    fun standardPostWithBodyTest() {
        val client = getRestClient()

        val request = RestRequestFactory.post {
            path(postPathWithBody)
            body(toJson(getProduct(0)))
            param(restParam)
        }

        val response = client.send(request)
        val body = response.body

        assertThatRequestWasSuccessfull(response)
        assert(body == toJson(getProduct(1)))
    }

    @Test
    fun standardGetTest_negativ() {
        val client = getRestClient()

        val request = RestRequestFactory.get {
            path("/random/path")
        }

        val response = client.send(request)
        assert(response.statusCode != 200)
    }

    @Test
    fun getRequestOnPostPath_negativ() {
        val client = getRestClient()
        val request = RestRequestFactory.get {
            path(postPathWithoutBody)
        }

        val response = client.send(request)
        assert(response.statusCode != 200)
    }

    @Test
    fun getRequestWithBearerAuth() {
        val client = getRestClient()
        val request = RestRequestFactory.get {
            path(getPathWithBearerAuthorization)
            auth(RequestAuth.bearer("token"))
        }
        assertThatRequestWasSuccessfull(client.send(request))
    }
    @Test
    fun getRequestWithBearerAuth_negativ() {
        val client = getRestClient()
        val request = RestRequestFactory.get {
            path(getPathWithBearerAuthorization)
        }
        val response = client.send(request)
        assert(response.statusCode != 200)
    }

    @Test
    fun putRequestTest(){
        val client = getRestClient()

        val request = RestRequestFactory.put {
            path(putPath)
        }

        val response = client.send(request)
        Assertions.assertEquals(200, response.statusCode)

    }

}
