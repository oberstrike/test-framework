package com.maju.rest

import io.restassured.response.Response

class RestResponse(private val response: Response) {

    val body: String = response.body().asString()

    val statusCode = response.statusCode

    val cookies: MutableMap<String, String> = response.cookies

    val contentType: String = response.contentType

    fun <T> convert(t: Class<T>): T {
        return response.`as`(t)
    }


}
