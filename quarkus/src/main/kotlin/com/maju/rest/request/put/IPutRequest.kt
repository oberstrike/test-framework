package com.maju.rest.request.put

import com.maju.rest.client.RestClient
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.request.post.IPostRequest
import com.maju.rest.request.post.PostRequest
import com.maju.rest.request.post.PostRequestHandler
import com.maju.rest.response.RestResponse
import io.restassured.specification.RequestSpecification

interface IPutRequest : IPostRequest

class PutRequest(path: String) : IPutRequest, PostRequest(path)

class PutRequestHandler : RestClient.OnRequestCreateHandler<IPutRequest> {

    private val postRequestHandler = PostRequestHandler()

    override fun onRequestCreate(request: IPutRequest, port: Int): RequestSpecification {
        return postRequestHandler.onRequestCreate(request, port)
    }

}
