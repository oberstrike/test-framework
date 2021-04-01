package com.maju.rest

import com.maju.rest.request.auth.RequestAuth
import com.maju.rest.request.delete.delete
import com.maju.rest.request.get.get
import com.maju.rest.request.patch.patch
import com.maju.rest.request.post.post
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

class RestRequestTest {

    private val params = mapOf("" to "")
    private val path = "home"
    private val body = "body"
    private val contentType = ContentType.JSON.contentTypeStrings.first()
    private val auth = RequestAuth.basic("oberstrike", "test123")


    @Test
    fun getRequestTest() {

        val request = get {
            path(path)
            params(params)
        }

        assert(request.path == path)
        assert(request.params == params)
    }

    @Test
    fun postRequestTest() {

        val request = post {
            params(params)
            body(body)
            path(path)
            contentType(contentType)
            auth(auth)
        }

        assert(request.params == params)
        assert(request.path == path)
        assert(request.body == body)
        assert(request.contentType == contentType)
        assert(request.requestAuth == auth)
    }

    @Test
    fun deleteRequestTest() {
        val request = delete {
            params(params)
            contentType(contentType)
            auth(auth)
            path(path)

        }

        assert(request.params == params)
        assert(request.path == path)
        assert(request.contentType == contentType)
        assert(request.requestAuth == auth)
        assert(request.contentType == contentType)
    }

    @Test
    fun patchRequestTest() {
        val request = patch {
            params(params)
            body(body)
            contentType(contentType)
            auth(auth)
            path(path)
        }


        assert(request.params == params)
        assert(request.path == path)
        assert(request.contentType == contentType)
        assert(request.requestAuth == auth)
        assert(request.contentType == contentType)
    }


}
