package com.maju.rest.request.patch

import com.maju.rest.client.RestClient
import com.maju.rest.request.auth.IRequestAuth
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.request.post.IPostRequest
import com.maju.rest.request.post.PostRequest
import com.maju.rest.request.post.PostRequestHandler
import com.maju.rest.response.RestResponse
import io.restassured.module.kotlin.extensions.Given
import io.restassured.specification.MultiPartSpecification
import io.restassured.specification.RequestSpecification

interface IPatchRequest : IPostRequest

class PatchRequest(post: IPostRequest, path: String) : IPatchRequest, IPostRequest by PostRequest(path) {
    override var contentType = post.contentType

    override var body = post.body

    override var multipartFile = post.multipartFile

    override var requestAuth: IRequestAuth<*>? = post.requestAuth

    override var params: Map<String, *>? = post.params

    override var headers: Map<String, String>? = post.headers

    override var cookies: Map<String, *>? = post.cookies

    override val requestSpecification: RequestSpecification = post.requestSpecification
}

class PatchRequestHandler : RestClient.OnRequestCreateHandler<IPatchRequest> {

    private val postRequestHandler = PostRequestHandler()

    override fun onRequestCreate(request: IPatchRequest, port: Int): RequestSpecification {
        return request.requestSpecification
            .apply { postRequestHandler.onRequestCreate(request, port) }
    }

}
