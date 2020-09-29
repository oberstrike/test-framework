package com.maju.rest.request

import com.maju.rest.RestClient
import com.maju.rest.RestRequestAuth
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification

interface IDeleteRequest : IGetRequest {
    var contentType: ContentType?
}

class DeleteRequest : IDeleteRequest {

    override lateinit var path: String

    override var restRequestAuth: RestRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override val headers: Map<String, String>? = null

    override var contentType: ContentType? = null
}

class DeleteRequestHandler : RestClient.OnRequestCreateHandler<IDeleteRequest> {

    private val getRequestHandler = GetRequestHandler(false)

    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IDeleteRequest
    ): RequestSpecification {
        getRequestHandler.onRequestCreate(requestSpecification, request)
        return requestSpecification.apply {
            applyDelete(request)
        }
    }

    private fun RequestSpecification.applyDelete(request: IDeleteRequest) {
        contentType(request.contentType)
    }
}