package com.maju.rest.request

import com.maju.rest.RestClient
import com.maju.rest.RestRequestAuth
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification

interface IPostRequest : IGetRequest {
    val contentType: ContentType?
    val body: String?
}

class PostRequest : IPostRequest {
    override lateinit var path: String

    override var restRequestAuth: RestRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override val headers: Map<String, String>? = null

    override var contentType: ContentType? = null

    override var body: String? = null
}


class PostRequestHandler : RestClient.OnRequestCreateHandler<IPostRequest> {

    var getRequestHandler = GetRequestHandler(false)

    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IPostRequest
    ): RequestSpecification {
        getRequestHandler.onRequestCreate(requestSpecification, request)
        return requestSpecification.apply {
            applyPost(request)
        }
    }


    private fun RequestSpecification.applyPost(request: IPostRequest) {
        if (request.contentType != null)
            contentType(request.contentType)
        if (request.body != null)
            body(request.body)
        if (request.params != null && request.body != null) {
            queryParams(request.params)
        } else {
            params(request.params)
        }
    }
}