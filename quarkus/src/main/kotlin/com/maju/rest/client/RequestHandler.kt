package com.maju.rest.client

import com.maju.rest.request.base.IBaseRequest
import com.maju.rest.request.delete.DeleteRequest
import com.maju.rest.request.delete.DeleteRequestHandler
import com.maju.rest.request.get.GetRequest
import com.maju.rest.request.get.GetRequestHandler
import com.maju.rest.request.get.IGetRequest
import com.maju.rest.request.pach.PatchRequest
import com.maju.rest.request.pach.PatchRequestHandler
import com.maju.rest.request.post.PostRequest
import com.maju.rest.request.post.PostRequestHandler
import com.maju.rest.request.put.PutRequest
import com.maju.rest.request.put.PutRequestHandler
import com.maju.rest.response.RestResponse
import io.restassured.specification.RequestSpecification

class RequestHandler<T : IBaseRequest> : RestClient.OnRequestCreateHandler<T> {

    private val getRequestHandler = GetRequestHandler()

    private val postRequestHandler = PostRequestHandler()

    private val deleteRequestHandler = DeleteRequestHandler()

    private val patchRequestHandler = PatchRequestHandler()

    private val putRequestHandler = PutRequestHandler()

    override fun onRequestCreate(
        requestSpecification: RequestSpecification,
        request: T
    ): RestResponse {
        return when (request) {

            is DeleteRequest -> {
                deleteRequestHandler.onRequestCreate(requestSpecification, request)
            }
            is PutRequest -> {
                putRequestHandler.onRequestCreate(requestSpecification, request)
            }
            is PatchRequest -> {
                patchRequestHandler.onRequestCreate(requestSpecification, request)
            }
            is PostRequest -> {
                postRequestHandler.onRequestCreate(requestSpecification, request)
            }
            is GetRequest -> {
                getRequestHandler.onRequestCreate(requestSpecification, request)
            }

            else -> throw Exception("There was no request-handler found: ${request.javaClass}")
        }
    }

}
