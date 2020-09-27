package com.maju.rest

import org.junit.jupiter.api.Test

class RestRequestTest {

    private val requestBuilder: IRestRequestBuilder = RestRequestBuilder.create("test")

    @Test
    fun requestTest() {
        val params = mapOf("" to "")
        val path = "home"

        requestBuilder.params(params)
        requestBuilder.path(path)

        val request = requestBuilder.build()
        assert(request.params == params)
        assert(request.path == path)

    }

}