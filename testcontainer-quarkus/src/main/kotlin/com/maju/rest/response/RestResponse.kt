package com.maju.rest.response

import io.restassured.response.Response

class RestResponse(private val response: Response) {

    val body: String = response.body().asString()

    val statusCode = response.statusCode

    val cookies: MutableMap<String, String> = response.cookies

    val headers: Map<String, String> =
        response.headers.asList().groupBy { it.name }.mapValues { it.value.map { p -> p.value }.first() }

    val contentType: String = response.contentType

    fun <T> convert(t: Class<T>): T {
        return response.`as`(t)
    }

    val sessionId: String = response.sessionId

}
