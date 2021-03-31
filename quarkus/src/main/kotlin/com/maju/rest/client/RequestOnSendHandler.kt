package com.maju.rest.client

import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.request.delete.DeleteRequest
import com.maju.rest.request.delete.DeleteRequestHandler
import com.maju.rest.request.get.GetRequest
import com.maju.rest.request.get.GetRequestHandler
import com.maju.rest.request.pach.PatchRequest
import com.maju.rest.request.pach.PatchRequestHandler
import com.maju.rest.request.post.PostRequest
import com.maju.rest.request.post.PostRequestHandler
import com.maju.rest.request.put.PutRequest
import com.maju.rest.request.put.PutRequestHandler
import com.maju.rest.response.RestResponse
import io.restassured.specification.RequestSpecification

class RequestOnSendHandler<T : IBaseRequest> : RestClient.OnSendHandler<T> {

    private val getRequestHandler = GetRequestHandler()

    private val postRequestHandler = PostRequestHandler()

    private val deleteRequestHandler = DeleteRequestHandler()

    private val patchRequestHandler = PatchRequestHandler()

    private val putRequestHandler = PutRequestHandler()

    override fun onSend(requestSpecification: RequestSpecification, request: T): RestResponse {
        return when (request) {
            is DeleteRequest -> {
                RestResponse(deleteRequestHandler.onRequestCreate(requestSpecification, request).delete(request.path))
            }
            is PutRequest -> {
                RestResponse(putRequestHandler.onRequestCreate(requestSpecification, request).put(request.path))
            }
            is PatchRequest -> {
                RestResponse(patchRequestHandler.onRequestCreate(requestSpecification, request).put(request.path))
            }
            is PostRequest -> {
                RestResponse(postRequestHandler.onRequestCreate(requestSpecification, request).put(request.path))
            }
            is GetRequest -> {
                RestResponse(getRequestHandler.onRequestCreate(requestSpecification, request).put(request.path))
            }

            else -> throw Exception("There was no request-handler found: ${request.javaClass}")
        }
    }

}
