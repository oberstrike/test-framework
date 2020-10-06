package com.maju.rest.request.pach

import com.maju.rest.client.RestClient
import com.maju.rest.request.post.IPostRequest
import com.maju.rest.request.post.PostRequest
import com.maju.rest.request.post.PostRequestHandler
import io.restassured.specification.RequestSpecification

interface IPatchRequest : IPostRequest

class PatchRequest : IPatchRequest, PostRequest()

class PatchRequestHandler : RestClient.OnRequestCreateHandler<IPatchRequest> {

    private val postRequestHandler = PostRequestHandler()

    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: IPatchRequest
    ): RequestSpecification {
        return requestSpecification.apply { postRequestHandler.onRequestCreate(this, request) }
    }

}