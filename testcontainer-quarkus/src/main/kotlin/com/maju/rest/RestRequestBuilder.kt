package com.maju.rest

import io.restassured.http.ContentType

interface IRestRequestBuilder {
    fun path(path: String): IRestRequestBuilder

    fun auth(restRequestAuth: RestRequestAuth<*>): IRestRequestBuilder

    fun params(params: Map<String, *>): IRestRequestBuilder

    fun contentType(contentType: ContentType): IRestRequestBuilder

    fun body(body: String): IRestRequestBuilder

    fun build(): IRestRequest
}

class RestRequestBuilder private constructor() : IRestRequestBuilder {

    companion object {
        fun create(path: String): IRestRequestBuilder = RestRequestBuilder().path(path)
    }

    private val restRequest = RestRequest()

    override fun build(): IRestRequest {
        return restRequest
    }

    override fun path(path: String): IRestRequestBuilder = apply {
        restRequest.path = path
    }

    override fun auth(restRequestAuth: RestRequestAuth<*>): IRestRequestBuilder = apply {
        restRequest.restRequestAuth = restRequestAuth
    }

    override fun params(params: Map<String, *>): IRestRequestBuilder = apply {
        restRequest.params = params
    }

    override fun contentType(contentType: ContentType): IRestRequestBuilder = apply {
        restRequest.contentType = contentType
    }

    override fun body(body: String) = apply {
        restRequest.body = body
    }

}