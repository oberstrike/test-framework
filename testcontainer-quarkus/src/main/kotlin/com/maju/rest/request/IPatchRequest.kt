package com.maju.rest.request

import com.maju.rest.RestClient
import com.maju.rest.RestRequestAuth
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification

interface IPatchRequest : IPostRequest {

}

class PatchRequest : IPatchRequest {

    override lateinit var path: String

    override var restRequestAuth: RestRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override val headers: Map<String, String>? = null

    override var contentType: ContentType? = null

    override var body: String? = null

}

class PatchRequestHandler : RestClient.OnRequestCreateHandler<IPatchRequest> {

    private val postRequestHandler = PostRequestHandler()

    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IPatchRequest
    ): RequestSpecification {
        return requestSpecification.apply { postRequestHandler.onRequestCreate(this, request) }
    }

}