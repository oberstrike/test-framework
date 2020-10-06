package com.maju.rest

import com.maju.rest.request.RestRequestFactory
import com.maju.rest.request.auth.BasicRequestAuth
import com.maju.rest.request.auth.RequestAuth
import com.maju.rest.request.delete.delete
import com.maju.rest.request.get.get
import com.maju.rest.request.pach.patch
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

        val request = RestRequestFactory.get {
            path(path)
            params(params)
        }

        assert(request.path == path)
        assert(request.params == params)
    }

    @Test
    fun postRequestTest() {

        val request = RestRequestFactory.post {
            params(params)
            path(path)
            body(body)
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
        val request = RestRequestFactory.delete {
            params(params)
            path(path)
            contentType(contentType)
            auth(auth)
        }

        assert(request.params == params)
        assert(request.path == path)
        assert(request.contentType == contentType)
        assert(request.requestAuth == auth)
        assert(request.contentType == contentType)
    }

    @Test
    fun patchRequestTest() {
        val request = RestRequestFactory.patch {
            params(params)
            path(path)
            body(body)
            contentType(contentType)
            auth(auth)
        }


        assert(request.params == params)
        assert(request.path == path)
        assert(request.contentType == contentType)
        assert(request.requestAuth == auth)
        assert(request.contentType == contentType)
    }


}