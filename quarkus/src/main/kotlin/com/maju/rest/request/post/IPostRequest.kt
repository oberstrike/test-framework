package com.maju.rest.request.post

import com.maju.rest.client.RestClient
import com.maju.rest.request.auth.IRequestAuth
import com.maju.rest.request.get.GetRequestHandler
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.response.RestResponse
import io.restassured.http.ContentType
import io.restassured.internal.RequestSpecificationImpl
import io.restassured.module.kotlin.extensions.Given
import io.restassured.specification.MultiPartSpecification
import io.restassured.specification.RequestSpecification
import java.io.File

interface IPostRequest : IGetRequest {
    var contentType: String?
    var body: Any?
    var multipartFile: MultiPartSpecification?

    override val requestSpecification: RequestSpecification
        get() = Given { log().all() }
}


open class PostRequest(override val path: String) : IPostRequest {

    override var requestAuth: IRequestAuth<*>? = null

    override var params: Map<String, *>? = null

    override var headers: Map<String, String>? = null

    override var contentType: String? = null

    override var body: Any? = null

    override var cookies: Map<String, *>? = null

    override var multipartFile: MultiPartSpecification? = null

}


class PostRequestHandler : RestClient.OnRequestCreateHandler<IPostRequest> {

    var getRequestHandler = GetRequestHandler(false)

    override fun onRequestCreate(request: IPostRequest, port: Int)
            : RequestSpecification {
        return getRequestHandler.onRequestCreate(request, port).apply {
            applyPost(request)
        }
    }

    private fun RequestSpecification.applyPost(request: IPostRequest) {
        if (request.contentType != null)
            contentType(request.contentType)
        if (request.body != null) {
            body(request.body)
            if (request.contentType == null) {
                contentType(ContentType.JSON)
            }
        }
        if (request.params != null && request.body != null) {
            queryParams(request.params)
        } else if (request.params != null) {
            params(request.params)
        }
        if (request.cookies != null)
            cookies(request.cookies)
        if (request.multipartFile != null)
            multiPart(request.multipartFile)

    }
}
