package com.maju.rest

import io.restassured.http.ContentType

interface IRestRequest {
    val path: String
    val restRequestAuth: RestRequestAuth<*>?
    val params: Map<String, *>?
    val contentType: ContentType?
    val body: String?
}


class RestRequest : IRestRequest {

    override lateinit var path: String

    override var restRequestAuth: RestRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override var contentType: ContentType? = null

    override var body: String? = null

}

