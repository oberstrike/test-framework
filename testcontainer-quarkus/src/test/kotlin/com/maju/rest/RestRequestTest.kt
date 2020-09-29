package com.maju.rest

import com.maju.rest.request.IPostRequest
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

class RestRequestTest {


    @Test
    fun getRequestTest() {
        val path = "/home"
        val params = mapOf("test" to "test123")

        val request = RestRequestBuilder.get() {
            path(path)
            params(params)
        }

        assert(request.path == path)
        assert(request.params == params)
    }

    @Test
    fun postRequestTest() {
        val params = mapOf("" to "")
        val path = "home"
        val body = "body"
        val contentType = ContentType.JSON
        val auth = BasicRestRequestAuth("oberstrike", "test123")


        val request = RestRequestBuilder.post {
            params(params)
            path(path)
            body(body)
            contentType(contentType)
            auth(auth)
        } as IPostRequest



        assert(request.params == params)
        assert(request.path == path)
        assert(request.body == body)
        assert(request.contentType == contentType)
        assert(request.restRequestAuth == auth)
    }

}