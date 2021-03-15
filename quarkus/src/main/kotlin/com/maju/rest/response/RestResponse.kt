package com.maju.rest.response

import io.restassured.response.Response

class RestResponse(response: Response) : Response by response {

    val body: String = response.body.asString()

    val headers: Map<String, String> = response.headers.asList().groupBy { it.name }.mapValues { it.value.map { p -> p.value }.first() }

}
