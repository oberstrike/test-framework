package com.maju.rest

import io.restassured.module.kotlin.extensions.Given
import io.restassured.specification.RequestSpecification

interface IRestClient {
    fun get(request: IRestRequest): RestResponse
    fun post(request: IRestRequest): RestResponse
    fun patch(request: IRestRequest): RestResponse
    fun delete(request: IRestRequest): RestResponse
}

class RestClient private constructor(
    private val basePath: String,
    private val port: Int
) : IRestClient {

    companion object {
        fun create(port: Int, basePath: String = ""): IRestClient = RestClient(basePath, port)
    }

    private val onRequestCreateBuilders: List<OnRequestCreateBuilder> = emptyList()

    override fun delete(request: IRestRequest): RestResponse {
        return RestResponse(createRequest(request).delete(request.path))
    }

    override fun get(request: IRestRequest): RestResponse {
        return RestResponse(createRequest(request).get(request.path))
    }

    override fun patch(request: IRestRequest): RestResponse {
        return RestResponse(createRequest(request).patch(request.path))
    }

    override fun post(request: IRestRequest): RestResponse {
        return RestResponse(createRequest(request).post(request.path))
    }

    private fun createRequest(request: IRestRequest): RequestSpecification {
        return Given {
            if (request.params != null)
                params(request.params)
            if (request.restRequestAuth != null)
                applyAuth(request.restRequestAuth!!)
            if (request.contentType != null)
                contentType(request.contentType)
            if (request.body != null)
                body(request.body)
            onRequestCreateBuilders.forEach {
                it.onRequestCreate(this)
            }

            log().all()
            port(port)
            basePath(basePath)
        }

    }

    interface OnRequestCreateBuilder {
        fun onRequestCreate(requestSpecification: RequestSpecification): RequestSpecification
    }
}

private fun RequestSpecification.applyAuth(
    auth: RestRequestAuth<*>
): RequestSpecification {
    return when (auth) {
        is BasicRestRequestAuth -> auth().preemptive().basic(auth.username, auth.password)
        is BearerRestRequestAuth -> auth().preemptive().oauth2(auth.bearerToken)
        else -> this
    }
}